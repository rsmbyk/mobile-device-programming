package com.rsmbyk.course.mdp.ui.upload

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.ui.camera.CameraActivity
import com.rsmbyk.course.mdp.ui.upload.rv.UploadImageListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_upload.*
import java.io.File
import javax.inject.Inject

class UploadFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: UploadViewModel

    @Inject
    lateinit var permissionUtil: PermissionUtil

    private lateinit var adapter: UploadImageListAdapter

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_upload, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        setupUploadList (viewModel.initializeUploadList ())
        setupObserver ()
    }

    private fun setupUploadList (uploadImages: List<UploadImageModel>) {
        adapter = UploadImageListAdapter (uploadImages, ::onUploadImageItemClicked)
        upload_list.setHasFixedSize (true)
        upload_list.addItemDecoration (DividerItemDecoration (context, RecyclerView.VERTICAL))
        upload_list.adapter = adapter
    }

    private fun setupObserver () {
        viewModel.addedUploadImageIndex.observe (this, Observer (::onImageAdded))
        viewModel.changedUploadImageIndex.observe (this, Observer { it?.also (adapter::notifyItemChanged) })
        viewModel.error.observe (this, Observer (::onUploadFailure))
    }

    private fun onUploadImageItemClicked (index: Int) {
        permissionUtil.requestPermission (
            Manifest.permission.CAMERA, ::openCamera, requestCode = index)
    }

    private fun openCamera (index: Int) =
        startActivityForResult (Intent (context!!, CameraActivity::class.java), index)

    private fun onImageAdded (index: Int?) {
        index?.also {
            upload_list.smoothScrollToPosition (index)
            adapter.notifyItemInserted (index)
        }
    }

    private fun onUploadFailure (t: Throwable?) {
        val message = t?.localizedMessage ?: getString (R.string.default_error_message)
        Snackbar
            .make (upload_root_view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction (R.string.btn_retry) { viewModel.startUpload () }
            .show ()
    }

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.also {
                val picturePath = it.getStringExtra (CameraActivity.PICTURE_PATH_EXTRA)
                val image = File (picturePath).readBytes ()
                viewModel.setUploadImage (requestCode, image)
                viewModel.startUpload ()
            }
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        permissionUtil.handlePermissionResult (requestCode, grantResults)
}

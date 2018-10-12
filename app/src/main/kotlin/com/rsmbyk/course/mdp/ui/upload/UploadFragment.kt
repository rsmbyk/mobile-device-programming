package com.rsmbyk.course.mdp.ui.upload

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.PermissionUtil
import com.rsmbyk.course.mdp.common.setVisible
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
        viewModel.changedUploadImageIndex.observe (this, Observer (adapter::updateImage))
        viewModel.uploadList.observe (this, Observer (::onUploadListChanged))
        viewModel.error.observe (this, Observer (::onUploadFailure))
        btn_upload.setOnClickListener { viewModel.startUpload () }
        btn_clear.setOnClickListener { viewModel.clearUploadList () }
        btn_retry.setOnClickListener { retryUpload () }
    }

    private fun setupUploadList (uploadList: List<UploadImageModel>) {
        adapter = UploadImageListAdapter (uploadList, ::onUploadImageItemClicked, ::onUploadImageItemLongClicked)
        upload_list.adapter = adapter
        upload_list.layoutManager = LinearLayoutManager (context)
        upload_list.setHasFixedSize (true)
        upload_list.addItemDecoration (DividerItemDecoration (context, RecyclerView.VERTICAL))
    }

    private fun onUploadListChanged (uploadList: List<UploadImageModel>?) {
        uploadList?.apply {
            upload_control.setVisible (all { it.image != null && it.state != UploadImageModel.State.UPLOADING })
            btn_upload.setVisible (all { it.image != null && it.state == UploadImageModel.State.IDLE })
        }
    }

    private fun onUploadImageItemClicked (index: Int) {
        if (viewModel.canUpdateItem) {
            permissionUtil.requestPermission (
                Manifest.permission.CAMERA, ::openCamera, requestCode = index)
        }
    }

    private fun openCamera (index: Int) =
        startActivityForResult (Intent (context!!, CameraActivity::class.java), index)

    private fun onUploadImageItemLongClicked (index: Int): Boolean {
        return viewModel.canUpdateItem.also {
            if (it)
                viewModel.removeUploadImage (index)
        }
    }

    private fun onUploadFailure (t: Throwable?) {
        btn_retry.visibility = View.VISIBLE
    }

    private fun retryUpload () {
        btn_retry.visibility = View.GONE
        viewModel.startUpload ()
    }

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.also {
                val picturePath = it.getStringExtra (CameraActivity.PICTURE_PATH_EXTRA)
                val image = File (picturePath).readBytes ()
                viewModel.setUploadImage (requestCode, image)
            }
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        permissionUtil.handlePermissionResult (requestCode, grantResults)
}

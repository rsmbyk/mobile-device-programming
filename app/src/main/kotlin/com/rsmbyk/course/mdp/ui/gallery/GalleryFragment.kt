package com.rsmbyk.course.mdp.ui.gallery

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.leinardi.android.speeddial.SpeedDialView
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.PermissionUtil
import com.rsmbyk.course.mdp.common.SpaceItemDecoration
import com.rsmbyk.course.mdp.ui.camera.CameraActivity
import com.rsmbyk.course.mdp.ui.camera.CameraFragment
import com.rsmbyk.course.mdp.ui.gallery.rv.GalleryAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject

class GalleryFragment: DaggerFragment () {

    companion object {
        private const val TAKE_PICTURE_REQUEST_CODE = 0
    }

    @Inject
    lateinit var viewModel: GalleryViewModel

    @Inject
    lateinit var permissionUtil: PermissionUtil

    private val adapter = GalleryAdapter ()

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_gallery, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        image_list.addItemDecoration (SpaceItemDecoration (context!!, spanCount = getSpanCount ()))
        image_list.layoutManager = GridLayoutManager (context, getSpanCount ())
        image_list.adapter = adapter
        fab_camera.setOnChangeListener (object: SpeedDialView.OnChangeListener {
            override fun onMainActionSelected (): Boolean {
                requestTakePicturePermissions ()
                return false
            }

            override fun onToggleChanged (isOpen: Boolean) {}
        })
        viewModel.images.observe (this, Observer (::setupImageList))
        requestReadStoragePermission ()
    }

    private fun requestReadStoragePermission () {
        permissionUtil.requestPermission (
            Manifest.permission.READ_EXTERNAL_STORAGE,
            viewModel::getImages,
            ::onReadStoragePermissionDenied)
    }

    private fun onReadStoragePermissionDenied () {
        Snackbar
            .make (coordinator, R.string.read_storage_permission_denied, Snackbar.LENGTH_INDEFINITE)
            .setAction (R.string.revoke_permission) { requestReadStoragePermission () }
            .show ()
    }

    private fun setupImageList (images: List<ByteArray>?) {
        images?.let (adapter::setImages)
    }

    private fun getSpanCount () =
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4

    private fun requestTakePicturePermissions () {
        permissionUtil.requestPermission (
            arrayOf (Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            ::openCameraActivity,
            ::onTakePicturePermissionDenied)
    }

    private fun openCameraActivity () =
        startActivityForResult (Intent (context!!, CameraActivity::class.java), TAKE_PICTURE_REQUEST_CODE)

    private fun onTakePicturePermissionDenied () =
        Toast.makeText (context!!, R.string.no_take_picture_permission, Toast.LENGTH_LONG).show ()

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val image = it.getByteArrayExtra (CameraFragment.PICTURE_EXTRA_NAME)
                viewModel.saveImage (image)
                adapter.addImage (image)
                image_list.smoothScrollToPosition (0)
                image_list.adapter = adapter
            }
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults)
        permissionUtil.handlePermissionResult (requestCode, grantResults)
    }
}

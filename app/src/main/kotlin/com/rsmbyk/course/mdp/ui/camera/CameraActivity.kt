package com.rsmbyk.course.mdp.ui.camera

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import com.leinardi.android.speeddial.SpeedDialView
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.common.SpaceItemDecoration
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_camera.*
import javax.inject.Inject

class CameraActivity: DaggerAppCompatActivity () {

    @Inject
    lateinit var viewModel: CameraViewModel

    @Inject
    lateinit var cameraUtil: CameraUtil

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_camera)
        fab_camera.setOnChangeListener (object: SpeedDialView.OnChangeListener {
            override fun onMainActionSelected (): Boolean {
                cameraUtil.openCamera (viewModel.imageDirectory)
                return false
            }

            override fun onToggleChanged (isOpen: Boolean) {}
        })
        cameraUtil.requestPermissions ()
    }

    private fun setupImageList () {
        image_list.addItemDecoration (SpaceItemDecoration (this, 4, getSpanCount ()))
        image_list.layoutManager = GridLayoutManager (this, getSpanCount ())
        image_list.adapter = viewModel.imageListAdapter
    }

    private fun getSpanCount () =
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult (requestCode, resultCode, data)
        if (requestCode == CameraUtil.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            viewModel.addCapturedImage ()
            image_list.smoothScrollToPosition (0)

            val broadcastIntent = Intent (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            broadcastIntent.setData (Uri.fromFile (viewModel.imageDirectory))
            sendBroadcast (broadcastIntent)
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults)
        if (requestCode == CameraUtil.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupImageList ()
        } else {
            Snackbar
                .make (coordinator, getString (R.string.snackbar_storage_permission_denied), Snackbar.LENGTH_INDEFINITE)
                .setAction (getString (R.string.snackbar_action_storage_permission_denied)) { cameraUtil.requestPermissions () }
                .show ()
        }
    }
}

package com.rsmbyk.course.mdp.ui.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraListener
import com.rsmbyk.course.mdp.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File

class CameraActivity: AppCompatActivity () {

    companion object {
        const val PICTURE_PATH_EXTRA = "com.rsmbyk.course.mdp.ui.camera.CameraActivity.PICTURE_EXTRA"
    }

    private val cameraListener = object : CameraListener () {
        override fun onPictureTaken (jpeg: ByteArray?) {
            val tempFile = File.createTempFile ("MDP-", null)
            tempFile.deleteOnExit ()
            tempFile.writeBytes (jpeg!!)

            val intent = Intent ().putExtra (PICTURE_PATH_EXTRA, tempFile.canonicalPath)
            setResult (Activity.RESULT_OK, intent)
            finish ()
        }
    }

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_camera)
        camera.setLifecycleOwner (this)
        camera.addCameraListener (cameraListener)
        btn_capture.setOnClickListener { camera.capturePicture () }
    }
}

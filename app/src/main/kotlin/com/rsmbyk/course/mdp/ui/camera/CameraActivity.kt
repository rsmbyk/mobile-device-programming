package com.rsmbyk.course.mdp.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraUtils
import com.rsmbyk.course.mdp.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraActivity: AppCompatActivity () {

    companion object {
        const val PICTURE_PATH_EXTRA = "com.rsmbyk.course.mdp.ui.camera.CameraActivity.PICTURE_EXTRA"
    }

    private val cameraListener = object : CameraListener () {
        override fun onPictureTaken (picture: ByteArray?) {
            CameraUtils.decodeBitmap (picture, 90, 90) {
                try {
                    val tempFile = File.createTempFile ("MDP-", null)
                    tempFile.deleteOnExit ()

                    FileOutputStream (tempFile.canonicalPath).use {
                        out -> it.compress (Bitmap.CompressFormat.PNG, 100, out) }

                    val intent = Intent ().putExtra (PICTURE_PATH_EXTRA, tempFile.canonicalPath)
                    setResult (Activity.RESULT_OK, intent)
                    finish ()
                } catch (e: IOException) {
                    e.printStackTrace ()
                    camera.start ()
                }
            }
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

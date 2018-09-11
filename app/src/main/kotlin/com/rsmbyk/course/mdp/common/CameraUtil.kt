package com.rsmbyk.course.mdp.common

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.rsmbyk.course.mdp.R
import java.io.File

class CameraUtil (private val activity: Activity) {

    companion object {
        const val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1
        const val REQUEST_IMAGE_CAPTURE = 2
    }

    private fun hasPermissions () =
        ContextCompat.checkSelfPermission (activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    fun requestPermissions () {
        ActivityCompat.requestPermissions (
            activity,
            arrayOf (Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
    }

    fun openCamera (outputImageDirectory: File) {
        try {
            require (hasPermissions ()) {
                "WRITE_EXTERNAL_STORAGE Permission is required"
            }

            val intent = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (createOutputFile (outputImageDirectory)))
            activity.startActivityForResult (intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: IllegalArgumentException) {
            Toast.makeText (activity, activity.getString (R.string.toast_no_storage_permission), Toast.LENGTH_SHORT).show ()
        }
    }

    private fun createOutputFile (outputImageDirectory: File): File =
        File.createTempFile (
            activity.getString (R.string.image_file_prefix),
            activity.getString (R.string.image_file_suffix),
            outputImageDirectory)
}

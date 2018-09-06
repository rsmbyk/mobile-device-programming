package com.rsmbyk.course.mdp.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.rsmbyk.course.mdp.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.IOException
import javax.inject.Inject

class CameraActivity: DaggerAppCompatActivity () {

    companion object {
        private const val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
    }

    @Inject
    lateinit var viewModel: CameraViewModel

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_camera)
        fab_camera.setOnClickListener { openCameraApp () }
        checkWritePermission ()
    }

    private fun setupImageList () {
        images.layoutManager = GridLayoutManager (this, 4)
        images.adapter = ImageAdapter (ArrayList (viewModel.getImages ()))
    }

    private fun checkWritePermission () {
        if (hasWritePermission ()) {
            setupImageList ()
        } else {
            ActivityCompat.requestPermissions (
                this,
                arrayOf (Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun hasWritePermission (): Boolean =
        ContextCompat.checkSelfPermission (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun openCameraApp () {
        if (hasWritePermission ()) {
            Intent (MediaStore.ACTION_IMAGE_CAPTURE).let {
                it.resolveActivity (getPackageManager ()) ?: return
                val photoFile: File = createImageFile ()
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile (photoFile))
                startActivityForResult (it, REQUEST_IMAGE_CAPTURE)
            }
        } else {
            Toast.makeText (this, getString (R.string.toast_no_storage_permission), Toast.LENGTH_SHORT).show ()
        }
    }

    @Throws (IOException::class)
    private fun createImageFile (): File =
        getOutputDirectory ().let {
            if (!it.exists ())
                it.mkdirs ()
            File.createTempFile ("MDP-", ".jpg", it)
        }

    private fun getOutputDirectory () =
        File (Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES), "MDP")

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult (requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (images.adapter as ImageAdapter).setData (viewModel.getImages ().toMutableList ())
            images.smoothScrollToPosition (0)
            sendBroadcast (Intent (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData (Uri.fromFile (getOutputDirectory ())))
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupImageList ()
        } else {
            Snackbar
                .make (coordinator, getString (R.string.snackbar_storage_permission_denied), Snackbar.LENGTH_INDEFINITE)
                .setAction (getString (R.string.snackbar_action_storage_permission_denied)) { checkWritePermission () }
                .show ()
        }
    }
}

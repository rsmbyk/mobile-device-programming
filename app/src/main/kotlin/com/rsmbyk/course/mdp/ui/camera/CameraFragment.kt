package com.rsmbyk.course.mdp.ui.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otaliastudios.cameraview.CameraListener
import com.rsmbyk.course.mdp.R
import kotlinx.android.synthetic.main.fragment_camera.*

class CameraFragment: Fragment () {

    companion object {
        const val PICTURE_EXTRA_NAME = "com.rsmbyk.course.mdp.ui.camera.GalleryFragment.PICTURE"
    }

    private val cameraListener = object : CameraListener () {
        override fun onPictureTaken (jpeg: ByteArray?) {
            val intent = Intent ().putExtra (PICTURE_EXTRA_NAME, jpeg)
            activity!!.apply {
                setResult (Activity.RESULT_OK, intent)
                finish ()
            }
        }
    }

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_camera, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        camera.setLifecycleOwner (this)
        camera.addCameraListener (cameraListener)
        btn_capture.setOnClickListener { camera.captureSnapshot () }
    }
}

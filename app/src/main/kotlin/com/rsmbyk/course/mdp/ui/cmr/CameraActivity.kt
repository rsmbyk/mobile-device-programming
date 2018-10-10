package com.rsmbyk.course.mdp.ui.cmr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rsmbyk.course.mdp.R

class CameraActivity: AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView (R.layout.activity_camera)
        supportFragmentManager
            .beginTransaction ()
            .replace (R.id.fragment, CameraFragment (), CameraFragment::javaClass.name)
            .commit ()
    }
}

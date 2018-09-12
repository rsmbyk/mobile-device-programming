package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog.Builder
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import com.leinardi.android.speeddial.SpeedDialView
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.R.layout
import com.rsmbyk.course.mdp.R.string
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.ui.networking.NetworkingViewModel.UploadImageCallback
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_networking.*
import javax.inject.Inject

private fun View.show () {
    visibility = View.VISIBLE
}

private fun View.hide () {
    visibility = View.GONE
}

class NetworkingActivity: DaggerAppCompatActivity () {

    @Inject
    lateinit var viewModel: NetworkingViewModel

    @Inject
    lateinit var cameraUtil: CameraUtil

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (layout.activity_networking)
        cameraUtil.requestPermissions ()
        viewModel.state.observe (this, Observer (::handleViewState))
        btn_clear.setOnClickListener { viewModel.clearUploadList () }
        btn_upload.setOnClickListener {
            progressBar.show ()
            btn_clear.hide ()
            btn_upload.hide ()
            fab_add.hide ()

            viewModel.uploadImages (
                getString (string.url_endpoint),
                getString (string.nrp),
                object : UploadImageCallback {
                    override fun onError (throwable: Throwable) {
                        Builder (this@NetworkingActivity)
                            .setCancelable(false)
                            .setMessage (throwable.message)
                            .setPositiveButton ("Close") { _, _ -> }
                            .create ()
                            .show ()
                    }

                    override fun onFinished () {

                    }
                }
            )
        }
    }

    private fun setupFab () {
        fab_add.inflate (R.menu.fab_add)
        fab_add.setOnChangeListener (object: SpeedDialView.OnChangeListener {
            override fun onMainActionSelected (): Boolean {
                cameraUtil.openCamera (viewModel.uploadImageDirectory)
                return false
            }

            override fun onToggleChanged (isOpen: Boolean) {}
        })
        fab_add.setOnActionSelectedListener {
            false
        }
    }

    private fun setupUploadList () {
        upload_list.setHasFixedSize (true)
        upload_list.addItemDecoration (DividerItemDecoration (this, RecyclerView.VERTICAL))
        upload_list.layoutManager = LinearLayoutManager (this)
        upload_list.adapter = viewModel.uploadListAdapter
    }

    private fun handleViewState (state: NetworkingViewState?) {
        state ?: return

        if (state.isUploading) {
            btn_upload.hide ()
            btn_clear.hide ()
            fab_add.hide ()
            progressBar.show ()
            progressBar.progress = (state.uploadIndex+1) / state.uploadTotal * 100
        }
        else {
            val params = fab_add.layoutParams as CoordinatorLayout.LayoutParams
            if (state.uploadTotal > 0) {
                upload_utils.show ()
                val density = resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
                params.setMargins (0, 0, 0, 48 * density)
            } else {
                upload_utils.hide ()
                params.setMargins (0, 0, 0, 0)
            }
            btn_upload.show ()
            btn_clear.show ()
            fab_add.show ()
            fab_add.layoutParams = params
            progressBar.hide ()
        }
    }

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult (requestCode, resultCode, data)
        if (requestCode == CameraUtil.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            viewModel.addCapturedImage ()
            upload_list.smoothScrollToPosition (viewModel.uploadList.size - 1)
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults)
        if (requestCode == CameraUtil.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupUploadList ()
            setupFab ()
        } else {
            Snackbar
                .make (coordinator, getString (R.string.snackbar_storage_permission_denied), Snackbar.LENGTH_INDEFINITE)
                .setAction (getString (R.string.snackbar_action_storage_permission_denied)) { cameraUtil.requestPermissions () }
                .show ()
        }
    }

    override fun onDestroy () {
        super.onDestroy ()
        viewModel.state.removeObservers (this)
    }
}

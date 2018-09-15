package com.rsmbyk.course.mdp.ui.networking

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leinardi.android.speeddial.SpeedDialView
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.common.hide
import com.rsmbyk.course.mdp.common.setVisible
import com.rsmbyk.course.mdp.common.show
import com.rsmbyk.course.mdp.ui.networking.NetworkingViewState.UploadState
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_networking.*
import javax.inject.Inject

class NetworkingFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: NetworkingViewModel

    @Inject
    lateinit var cameraUtil: CameraUtil

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.activity_networking, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        cameraUtil.requestPermissions ()
        viewModel.state.observe (this, Observer (::handleViewState))
        btn_clear.setOnClickListener { viewModel.clearUploadList () }
        btn_upload.setOnClickListener {
            progress_bar.show ()
            btn_clear.hide ()
            btn_upload.hide ()
            fab_add.hide ()
            viewModel.uploadImages (getString (R.string.nrp))
        }
    }

    private fun setupFab () {
        fab_add.setOnChangeListener (object: SpeedDialView.OnChangeListener {
            override fun onMainActionSelected (): Boolean {
                cameraUtil.openCamera (viewModel.uploadImageDirectory)
                return false
            }

            override fun onToggleChanged (isOpen: Boolean) {}
        })
    }

    private fun setupUploadList () {
        upload_list.adapter = viewModel.uploadListAdapter
        upload_list.layoutManager = LinearLayoutManager (context)
        upload_list.setHasFixedSize (true)
        upload_list.addItemDecoration (DividerItemDecoration (context, RecyclerView.VERTICAL))
    }

    private fun handleViewState (state: NetworkingViewState?) {
        state ?: return

        if (state.uploadState == UploadState.UPLOADING) {
            btn_upload.hide ()
            btn_clear.hide ()
            fab_add.hide ()
            progress_bar.show ()
            progress_bar.progress = (state.uploadProgressIndex.toFloat () / state.uploadTotal.toFloat () * 100.0).toInt ()
        }
        else {
            val params = fab_add.layoutParams as CoordinatorLayout.LayoutParams
            if (state.uploadTotal > 0) {
                upload_control.show ()
                val density = resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
                params.setMargins (0, 0, 0, 48 * density)
            } else {
                upload_control.hide ()
                params.setMargins (0, 0, 0, 0)
            }
            btn_upload.show ()
            btn_upload.isEnabled = state.uploadState != UploadState.FINISHED
            btn_clear.show ()
            fab_add.show ()
            fab_add.setVisible (state.uploadState == UploadState.IDLE)
            fab_add.layoutParams = params
            progress_bar.hide ()
        }
    }

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CameraUtil.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            viewModel.addCapturedImage ()
            upload_list.smoothScrollToPosition (viewModel.uploadList.size - 1)
        }
    }

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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

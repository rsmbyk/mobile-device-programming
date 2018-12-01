package com.rsmbyk.course.mdp.ui.absent

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.handler.ZXHandler
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.model.AgendaModel
import com.rsmbyk.course.mdp.model.PredictResponseModel
import com.rsmbyk.course.mdp.ui.camera.CameraActivity
import dagger.android.support.DaggerFragment
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.io.File
import javax.inject.Inject

class AbsentFragment: DaggerFragment () {

    companion object {
        private const val CAPTURE_FACE_IMAGE_REQUEST = 0
    }

    @Inject lateinit var viewModel: AbsentViewModel
    @Inject lateinit var permissionUtil: PermissionUtil
    @Inject lateinit var zxHandler: ZXHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        ZXingScannerView (context!!)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.agenda.observe (this, Observer (::onAgendaLoaded))
        viewModel.currentLocation.observe (this, Observer (::onAgendaRange))
        viewModel.faceImage.observe (this, Observer (::onFaceCaptured))
        viewModel.predictResponse.observe (this, Observer (::onPredictionResponse))
        viewModel.error.observe (this, Observer (::showError))
        viewModel.notif.observe (this, Observer (::showNotif))
        permissionUtil.requestPermission (Manifest.permission.CAMERA, { _ ->
            zxHandler.result.observe (this, Observer (::handleQRResult))
            zxHandler.setResultHandler (view as ZXingScannerView)
        })
    }

    private fun handleQRResult (result: Result?) {
        viewModel.parseAgenda (result!!.text)
    }

    private fun onAgendaLoaded (agendaModel: AgendaModel?) =
        requestLocationPermission ()

    private fun onAgendaRange (currentLocation: Location?) {
        zxHandler.stop ()
        startActivityForResult (Intent (context!!, CameraActivity::class.java), CAPTURE_FACE_IMAGE_REQUEST)
    }

    private fun onFaceCaptured (faceImage: ByteArray?) =
        viewModel.predict ("5115100174", "ronald123")

    private fun requestLocationPermission () {
        permissionUtil.requestPermission (
            Manifest.permission.ACCESS_FINE_LOCATION,
            viewModel::calculateDistance,
            {
                showGenericError (
                    R.string.read_storage_permission_denied,
                    ::requestLocationPermission)
            })
    }

    private fun showGenericError (@StringRes resId: Int, listener: () -> Unit) {
        Snackbar.make (view!!, resId, Snackbar.LENGTH_INDEFINITE)
                .setAction (R.string.btn_retry) { listener () }
    }

    private fun onPredictionResponse (predictResponseModel: PredictResponseModel?) {
        val builder = AlertDialog.Builder (context!!)
        builder.setTitle ("Prediction Response")
        builder.setMessage (predictResponseModel!!.msg)
        builder.setCancelable (false)
        builder.setPositiveButton ("Close", { _, _ -> })
        builder.create ().show ()
    }

    private fun showError (error: Throwable?) {
        Snackbar.make (view!!, error?.message!!, Snackbar.LENGTH_INDEFINITE).show ()
    }

    private fun showNotif (notif: Throwable?) {
        Toast.makeText (context!!, notif!!.message, Toast.LENGTH_LONG).show ()
    }

    override fun onResume () {
        super.onResume ()
        permissionUtil.requestPermission (Manifest.permission.CAMERA, zxHandler::start)
    }

    override fun onPause () {
        super.onPause ()
        zxHandler.stop ()
    }

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAPTURE_FACE_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val picturePath = it.getStringExtra (CameraActivity.PICTURE_PATH_EXTRA)
                    val image = File (picturePath).readBytes ()
                    viewModel.setFaceImage (image)
                }
            }
            else {
                zxHandler.resume ()
            }
        }
    }
}

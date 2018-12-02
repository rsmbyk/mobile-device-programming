package com.rsmbyk.course.mdp.ui.absent

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.R.string
import com.rsmbyk.course.mdp.common.handler.ZXHandler
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.model.AgendaModel
import com.rsmbyk.course.mdp.model.SuperResponseModel
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

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        ZXingScannerView (context!!)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog (context!!)
        viewModel.agenda.observe (this, Observer (::onAgendaLoaded))
        viewModel.currentLocation.observe (this, Observer (::onAgendaRange))
        viewModel.faceImage.observe (this, Observer (::onFaceCaptured))
        viewModel.predictResponse.observe (this, Observer (::onPredictionResponse))
        viewModel.error.observe (this, Observer (::showError))
        viewModel.notif.observe (this, Observer (::showNotif))
        viewModel.isBusy.observe (this, Observer (::handleState))
        zxHandler.result.observe (this, Observer (::handleQRResult))
        showCredentialsDialog ()
    }

    private fun showCredentialsDialog () {
        val builder = AlertDialog.Builder (context!!)
        builder.setTitle ("Enter your credentials")
        builder.setCancelable (false)

        val dialogView = LayoutInflater
            .from (context)
            .inflate (R.layout.fragment_credentials_input, view as ViewGroup?, false)

        builder.setView (dialogView)

        builder.setPositiveButton (android.R.string.ok) { dialog, which ->
            val idUser = dialogView.findViewById<TextInputEditText> (R.id.idUser)
            val password = dialogView.findViewById<TextInputEditText> (R.id.password)

            if (idUser.text == null || password.text == null)
                return@setPositiveButton
            viewModel.setCredentials (idUser.text.toString (), password.text.toString ())
            dialog.dismiss ()
            zxHandler.setResultHandler (view as ZXingScannerView)
            requestCameraPermission () }

        builder.show ()
    }

    private fun requestCameraPermission () {
        permissionUtil.requestPermission (
            Manifest.permission.CAMERA,
            { _ ->
                zxHandler.start () },
            { _ ->
                onPermissionDenied (
                    string.location_permission_denied, ::requestCameraPermission)
            }
        )
    }

    private fun handleQRResult (result: Result?) {
        viewModel.parseAgenda (result!!.text)
    }

    private fun onAgendaLoaded (agendaModel: AgendaModel?) {
        progressDialog.setMessage ("Getting your location")
        requestLocationPermission ()
    }

    private fun onAgendaRange (currentLocation: Location?) {
        zxHandler.stop ()
        progressDialog.setMessage ("Getting your face")
        startActivityForResult (
            Intent (context!!, CameraActivity::class.java), CAPTURE_FACE_IMAGE_REQUEST)
    }

    private fun onFaceCaptured (faceImage: ByteArray?) {
        progressDialog.setMessage ("Signing in")
        viewModel.predict ()
    }

    private fun requestLocationPermission () {
        permissionUtil.requestPermission (
            Manifest.permission.ACCESS_FINE_LOCATION,
            viewModel::calculateDistance,
            {
                onPermissionDenied (
                    string.read_storage_permission_denied,
                    ::requestLocationPermission)
            })
    }

    private fun onPermissionDenied (@StringRes resId: Int, listener: () -> Unit) {
        progressDialog.dismiss ()
        Snackbar.make (view!!, resId, Snackbar.LENGTH_INDEFINITE)
                .setAction (R.string.revoke_permission) { listener () }
                .show ()
    }

    private fun onPredictionResponse (predictResponseModel: SuperResponseModel?) {
        zxHandler.stop ()
        val builder = AlertDialog.Builder (context!!)
        builder.setTitle ("Prediction Response")
        builder.setMessage (predictResponseModel!!.msg)
        builder.setCancelable (false)
        builder.setPositiveButton ("Close") { _, _ -> showNotif (Throwable (predictResponseModel.msg)) }
        builder.create ().show ()
    }

    private fun handleState (isBusy: Boolean?) {
        isBusy?.let {
            if (it) {
                progressDialog.isIndeterminate = true
                progressDialog.show ()
            }
            else {
                progressDialog.dismiss ()
            }
        }
    }

    private fun showError (error: Throwable?) {
        val msg = error?.message ?: "Something went wrong"
        Snackbar.make (view!!, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction (R.string.btn_retry) { zxHandler.start () }
                .show ()
    }

    private fun showNotif (notif: Throwable?) {
        val msg = notif?.message ?: "Something went wrong"
        Toast.makeText (context!!, msg, Toast.LENGTH_LONG).show ()
    }

    override fun onResume () {
        super.onResume ()
        if (viewModel.isBusy.value == false)
            zxHandler.start ()
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

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        permissionUtil.handlePermissionResult (requestCode, grantResults)
}

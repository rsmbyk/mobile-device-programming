package com.rsmbyk.course.mdp.ui.absent

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.model.AgendaModel
import com.rsmbyk.course.mdp.model.CoordinateModel
import com.rsmbyk.course.mdp.model.PredictRequestModel
import com.rsmbyk.course.mdp.model.ResponseStatusModel
import com.rsmbyk.course.mdp.model.SuperResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AbsentViewModel (
    private val getPredictionUseCase: GetPredictionUseCase,
    private val locationClient: FusedLocationProviderClient,
    private val predictRequestModelMapper: Mapper<PredictRequest, PredictRequestModel>,
    private val predictResponseModelMapper: Mapper<SuperResponse, SuperResponseModel>)
        : ViewModel () {

    companion object {
        private const val ALLOWED_DISTANCE = 10f
    }

    private val disposable = CompositeDisposable ()

    private val agendaLocation = Location ("")
    private lateinit var credentials: Pair<String, String>
    val agenda = MutableLiveData<AgendaModel> ()
    val currentLocation = MutableLiveData<Location> ()
    val faceImage = MutableLiveData<ByteArray> ()
    val predictResponse = MutableLiveData<SuperResponseModel> ()
    val isBusy = MutableLiveData<Boolean> ()

    val error = MutableLiveData<Throwable> ()
    val notif = MutableLiveData<Throwable> ()

    fun setCredentials (idUser: String, password: String) {
        credentials = Pair (idUser, password)
    }

    fun parseAgenda (qrResult: String) {
        isBusy.value = true
        val splittedResult = qrResult.split (",")
        if (splittedResult.size != 3)
            error.value = IllegalArgumentException ("QRCode format unrecognized")

        try {
            val latitude = splittedResult[0]
            val longitude = splittedResult[1]
            val id = splittedResult[2]
            agenda.value = AgendaModel (id, CoordinateModel (latitude.toDouble (), longitude.toDouble ()))
            agendaLocation.latitude = latitude.toDouble ()
            agendaLocation.longitude = longitude.toDouble ()
        }
        catch (e: NumberFormatException) {
            error.value = IllegalArgumentException ("Invalid QRCode format")
        }
    }

    @SuppressLint("MissingPermission")
    fun calculateDistance () {
        locationClient.lastLocation.addOnSuccessListener {
            if (agendaLocation.distanceTo (it) <= ALLOWED_DISTANCE)
                currentLocation.value = it
            else {
                isBusy.value = false
                error.value = IllegalArgumentException ("Must be in 10 meters range")
            }
        }
    }

    fun setFaceImage (image: ByteArray) {
        faceImage.value = image
    }

    fun predict () {
        val request = PredictRequestModel (
            credentials.first, credentials.second, faceImage.value!!, agenda.value!!)

        disposable.add (
            predictRequestModelMapper.mapToEntity (request)
                .let (getPredictionUseCase::invoke)
                .map (predictResponseModelMapper::mapToModel)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .doOnSuccess (::onPredictSuccess)
                .doOnError (error::setValue)
                .doFinally { isBusy.value = false }
                .subscribe ())
    }

    private fun onPredictSuccess (response: SuperResponseModel) {
        if (response.status == ResponseStatusModel.ACCEPTED) {
            predictResponse.value = response
        } else {
            error.value = Throwable (response.msg)
        }
    }
}

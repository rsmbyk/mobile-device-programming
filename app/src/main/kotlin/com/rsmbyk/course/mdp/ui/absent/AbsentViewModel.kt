package com.rsmbyk.course.mdp.ui.absent

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.model.AgendaModel
import com.rsmbyk.course.mdp.model.CoordinateModel
import com.rsmbyk.course.mdp.model.PredictRequestModel
import com.rsmbyk.course.mdp.model.PredictResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AbsentViewModel (
    private val getPredictionUseCase: GetPredictionUseCase,
    private val locationClient: FusedLocationProviderClient,
    private val predictRequestModelMapper: Mapper<PredictRequest, PredictRequestModel>,
    private val predictResponseModelMapper: Mapper<PredictResponse, PredictResponseModel>)
        : ViewModel () {

    companion object {
        private const val ALLOWED_DISTANCE = 10f
    }

    private val disposable = CompositeDisposable ()

    private val agendaLocation = Location ("")
    val agenda = MutableLiveData<AgendaModel> ()
    val currentLocation = MutableLiveData<Location> ()
    val faceImage = MutableLiveData<ByteArray> ()
    val predictResponse = MutableLiveData<PredictResponseModel> ()
    val isCapturingFace = MutableLiveData<Boolean> ()

    val error = MutableLiveData<Throwable> ()
    val notif = MutableLiveData<Throwable> ()

    fun parseAgenda (qrResult: String) {
        val splittedResult = qrResult.split (",")
        if (splittedResult.size != 3)
            error.value = IllegalArgumentException ("QRCode format unrecognized")

        try {
            Log.e ("AbsentViewModel", qrResult)
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
        Log.e ("AbsentViewModel", "calculateDistance")
        locationClient.lastLocation.addOnSuccessListener {
            if (agendaLocation.distanceTo (it) <= ALLOWED_DISTANCE || true)
                currentLocation.value = it
            else
                error.value = IllegalArgumentException ("Must be in 10 meters range")
        }
    }

    fun setFaceImage (image: ByteArray) {
        faceImage.value = image
    }

    fun predict (idUser: String, password: String) {
        val request = PredictRequestModel (idUser, password, faceImage.value!!, agenda.value!!)
        disposable.add (
            predictRequestModelMapper.mapToEntity (request)
                .let (getPredictionUseCase::invoke)
                .map (predictResponseModelMapper::mapToModel)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .doOnSuccess (predictResponse::setValue)
                .doOnError (error::setValue)
                .subscribe ())
    }
}

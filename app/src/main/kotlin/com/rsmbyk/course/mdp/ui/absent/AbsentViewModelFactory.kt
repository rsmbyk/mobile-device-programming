package com.rsmbyk.course.mdp.ui.absent

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.model.PredictRequestModel
import com.rsmbyk.course.mdp.model.SuperResponseModel

class AbsentViewModelFactory (
    private val getPredictionUseCase: GetPredictionUseCase,
    private val locationClient: FusedLocationProviderClient,
    private val predictRequestModelMapper: Mapper<PredictRequest, PredictRequestModel>,
    private val predictResponseModelMapper: Mapper<SuperResponse, SuperResponseModel>)
        : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create (modelClass: Class<T>): T =
        AbsentViewModel (
            getPredictionUseCase,
            locationClient,
            predictRequestModelMapper,
            predictResponseModelMapper) as T
}

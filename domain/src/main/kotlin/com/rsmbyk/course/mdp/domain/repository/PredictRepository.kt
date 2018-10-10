package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import io.reactivex.Single

interface PredictRepository {

    fun getPrediction (request: PredictRequest): Single<PredictResponse>
}

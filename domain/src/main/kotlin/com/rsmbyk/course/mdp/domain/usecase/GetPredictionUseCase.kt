package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import io.reactivex.Single

class GetPredictionUseCase (private val repository: AttendanceRepository) {

    operator fun invoke (request: PredictRequest): Single<SuperResponse> =
        repository.getPrediction (request)
}

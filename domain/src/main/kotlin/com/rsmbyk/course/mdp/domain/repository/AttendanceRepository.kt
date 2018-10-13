package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import io.reactivex.Flowable
import io.reactivex.Single

interface AttendanceRepository {

    fun getColumnHeaders (): List<String>
    fun getStudents (): Flowable<List<Student>>
    fun getPrediction (request: PredictRequest): Single<PredictResponse>
}

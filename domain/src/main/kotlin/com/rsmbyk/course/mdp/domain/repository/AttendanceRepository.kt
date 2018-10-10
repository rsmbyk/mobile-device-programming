package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import io.reactivex.Single

interface AttendanceRepository {

    fun getColumnHeaderList (): List<String>
    fun getStudents (): Single<List<Student>>
    fun getPrediction (request: PredictRequest): Single<PredictResponse>
}

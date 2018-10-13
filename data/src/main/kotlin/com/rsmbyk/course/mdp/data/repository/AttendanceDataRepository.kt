package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.api.volley.request.PredictVolleyRequest
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.data.model.PredictResponseData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import io.reactivex.Flowable
import io.reactivex.Single

class AttendanceDataRepository (
    private val context: Context,
    private val studentDao: StudentDao,
    private val volleyRequestQueue: VolleyRequestQueue,
    private val studentEntityMapper: Mapper<Student, StudentEntity>,
    private val predictRequestMapper: Mapper<PredictRequest, PredictRequestData>,
    private val predictResponseMapper: Mapper<PredictResponse, PredictResponseData>)
        : AttendanceRepository {

    override fun getColumnHeaders (): List<String> =
        context.resources.getStringArray (R.array.attendance_column_header).toList ()

    override fun getStudents (): Flowable<List<Student>> =
        studentDao.all ().map (studentEntityMapper::mapToEntity)

    override fun getPrediction (request: PredictRequest): Single<PredictResponse> {
        return Single.create { emitter ->
            volleyRequestQueue.add (PredictVolleyRequest (
                context,
                predictRequestMapper.mapToModel (request),
                { emitter.onSuccess (predictResponseMapper.mapToEntity (it)) },
                emitter::onError))
        }
    }
}

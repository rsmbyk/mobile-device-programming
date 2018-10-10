package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import io.reactivex.Single

class AttendanceDataRepository (private val context: Context): AttendanceRepository {

    override fun getColumnHeaderList (): List<String> =
        context.resources.getStringArray (R.array.attendance_column_header).toList ()

    override fun getStudents (): Single<List<Student>> {
        return Single.create {
            it.onSuccess (listOf (
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)),
                Student ("05111540000174", "Ronald Andrean", listOf (true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true))
            ))
        }
    }

    override fun getPrediction (request: PredictRequest): Single<PredictResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

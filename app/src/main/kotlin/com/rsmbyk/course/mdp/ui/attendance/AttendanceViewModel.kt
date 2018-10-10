package com.rsmbyk.course.mdp.ui.attendance

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.usecase.GetAttendanceColumnHeadersUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetStudentsUseCase
import com.rsmbyk.course.mdp.model.PredictResponseModel
import com.rsmbyk.course.mdp.model.StudentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AttendanceViewModel (
    private val getAttendanceColumnHeadersUseCase: GetAttendanceColumnHeadersUseCase,
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getPredictionUseCase: GetPredictionUseCase,
    private val studentMapper: Mapper<Student, StudentModel>,
    private val predictResponseMapper: Mapper<PredictResponse, PredictResponseModel>)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val students = MutableLiveData<List<StudentModel>> ()
    val predictResult = MutableLiveData<PredictResponseModel> ()
    val error = MutableLiveData<Throwable> ()

    fun getColumnHeaderList (): List<String> =
        getAttendanceColumnHeadersUseCase ()

    fun getStudents () {
        disposable.add (getStudentsUseCase ()
            .map { it.map (studentMapper::mapToModel) }
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (students::setValue, error::setValue))
    }

    fun checkFace (row: Int, image: ByteArray) {
        disposable.add (getPredictionUseCase (PredictRequest (students.value!![row].nrp, image))
            .map (predictResponseMapper::mapToModel)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (predictResult::setValue, error::setValue))
    }

    override fun onCleared () =
        disposable.clear ()
}

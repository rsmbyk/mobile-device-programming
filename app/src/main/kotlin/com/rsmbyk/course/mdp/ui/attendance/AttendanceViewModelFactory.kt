package com.rsmbyk.course.mdp.ui.attendance

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.usecase.GetAttendanceColumnHeadersUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetStudentsUseCase
import com.rsmbyk.course.mdp.model.StudentModel
import com.rsmbyk.course.mdp.model.SuperResponseModel

class AttendanceViewModelFactory (
    private val getAttendanceColumnHeadersUseCase: GetAttendanceColumnHeadersUseCase,
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getPredictionUseCase: GetPredictionUseCase,
    private val studentMapper: Mapper<Student, StudentModel>,
    private val predictResponseMapper: Mapper<SuperResponse, SuperResponseModel>)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        AttendanceViewModel (
            getAttendanceColumnHeadersUseCase,
            getStudentsUseCase,
            getPredictionUseCase,
            studentMapper,
            predictResponseMapper) as T
}

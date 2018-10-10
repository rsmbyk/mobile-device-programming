package com.rsmbyk.course.mdp.ui.attendance

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.PermissionUtil
import com.rsmbyk.course.mdp.data.repository.AttendanceDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import com.rsmbyk.course.mdp.domain.usecase.GetAttendanceColumnHeadersUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetStudentsUseCase
import com.rsmbyk.course.mdp.mapper.PredictResponseModelMapper
import com.rsmbyk.course.mdp.mapper.StudentModelMapper
import com.rsmbyk.course.mdp.model.PredictResponseModel
import com.rsmbyk.course.mdp.model.StudentModel
import com.rsmbyk.course.mdp.ui.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class AttendanceFragmentModule {

    @Provides
    fun providePermissionUtil (activity: MainActivity): PermissionUtil =
        PermissionUtil (activity)

    @Provides
    fun provideAttendanceRepository (context: Context): AttendanceRepository =
        AttendanceDataRepository (context)

    @Provides
    fun provideGetAttendanceColumnHeadersUseCase (repository: AttendanceRepository): GetAttendanceColumnHeadersUseCase =
        GetAttendanceColumnHeadersUseCase (repository)

    @Provides
    fun provideGetStudentsUseCase (repository: AttendanceRepository): GetStudentsUseCase =
        GetStudentsUseCase (repository)

    @Provides
    fun provideGetPredictionUseCase (repository: AttendanceRepository): GetPredictionUseCase =
        GetPredictionUseCase (repository)

    @Provides
    fun provideStudentModelMapper (): Mapper<Student, StudentModel> =
        StudentModelMapper ()

    @Provides
    fun providePredictResponseModelMapper (): Mapper<PredictResponse, PredictResponseModel> =
        PredictResponseModelMapper ()

    @Provides
    fun provideViewModelFactory (getAttendanceColumnHeadersUseCase: GetAttendanceColumnHeadersUseCase, getStudentsUseCase: GetStudentsUseCase, getPredictionUseCase: GetPredictionUseCase, studentMapper: Mapper<Student, StudentModel>, predictResponseMapper: Mapper<PredictResponse, PredictResponseModel>): AttendanceViewModelFactory {
        return AttendanceViewModelFactory (
            getAttendanceColumnHeadersUseCase,
            getStudentsUseCase,
            getPredictionUseCase,
            studentMapper,
            predictResponseMapper)
    }

    @Provides
    fun provideViewModel (fragment: AttendanceFragment, factory: AttendanceViewModelFactory): AttendanceViewModel =
        ViewModelProviders.of (fragment, factory).get (AttendanceViewModel::class.java)
}

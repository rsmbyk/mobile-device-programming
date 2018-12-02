package com.rsmbyk.course.mdp.ui.attendance

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import com.rsmbyk.course.mdp.data.mapper.PredictRequestDataMapper
import com.rsmbyk.course.mdp.data.mapper.StudentEntityMapper
import com.rsmbyk.course.mdp.data.mapper.SuperResponseDataMapper
import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.data.model.SuperResponseData
import com.rsmbyk.course.mdp.data.repository.AttendanceDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import com.rsmbyk.course.mdp.domain.usecase.GetAttendanceColumnHeadersUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetStudentsUseCase
import com.rsmbyk.course.mdp.mapper.StudentModelMapper
import com.rsmbyk.course.mdp.mapper.SuperResponseModelMapper
import com.rsmbyk.course.mdp.model.StudentModel
import com.rsmbyk.course.mdp.model.SuperResponseModel
import dagger.Module
import dagger.Provides

@Module
class AttendanceFragmentModule {

    @Provides
    fun provideViewModel (fragment: AttendanceFragment, factory: AttendanceViewModelFactory): AttendanceViewModel =
        ViewModelProviders.of (fragment, factory).get (AttendanceViewModel::class.java)

    @Provides
    fun provideViewModelFactory (getAttendanceColumnHeadersUseCase: GetAttendanceColumnHeadersUseCase, getStudentsUseCase: GetStudentsUseCase, getPredictionUseCase: GetPredictionUseCase, studentModelMapper: Mapper<Student, StudentModel>, predictResponseModelMapper: Mapper<SuperResponse, SuperResponseModel>): AttendanceViewModelFactory {
        return AttendanceViewModelFactory (
            getAttendanceColumnHeadersUseCase, getStudentsUseCase, getPredictionUseCase, studentModelMapper, predictResponseModelMapper)
    }

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
    fun provideAttendanceRepository (context: Context, studentDao: StudentDao, volleyRequestQueue: VolleyRequestQueue, studentEntityMapper: Mapper<Student, StudentEntity>, predictRequestDataMapper: Mapper<PredictRequest, PredictRequestData>, predictResponseDataMapper: Mapper<SuperResponse, SuperResponseData>): AttendanceRepository {
        return AttendanceDataRepository (
            context, studentDao, volleyRequestQueue, studentEntityMapper, predictRequestDataMapper, predictResponseDataMapper)
    }

    @Provides
    fun provideStudentEntityMapper (): Mapper<Student, StudentEntity> =
        StudentEntityMapper ()

    @Provides
    fun providePredictRequestDataMapper (): Mapper<PredictRequest, PredictRequestData> =
        PredictRequestDataMapper ()

    @Provides
    fun providePredictResponseDataMapper (): Mapper<SuperResponse, SuperResponseData> =
        SuperResponseDataMapper ()

    @Provides
    fun provideStudentModelMapper (): Mapper<Student, StudentModel> =
        StudentModelMapper ()

    @Provides
    fun providePredictResponseModelMapper (): Mapper<SuperResponse, SuperResponseModel> =
        SuperResponseModelMapper ()

    @Provides
    fun providePermissionUtil (fragment: AttendanceFragment): PermissionUtil =
        PermissionUtil (fragment = fragment)
}

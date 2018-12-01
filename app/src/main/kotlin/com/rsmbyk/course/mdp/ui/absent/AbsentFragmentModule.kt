package com.rsmbyk.course.mdp.ui.absent

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.rsmbyk.course.mdp.common.handler.ZXHandler
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import com.rsmbyk.course.mdp.data.mapper.PredictRequestDataMapper
import com.rsmbyk.course.mdp.data.mapper.PredictResponseDataMapper
import com.rsmbyk.course.mdp.data.mapper.StudentEntityMapper
import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.data.model.PredictResponseData
import com.rsmbyk.course.mdp.data.repository.AttendanceDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import com.rsmbyk.course.mdp.domain.usecase.GetPredictionUseCase
import com.rsmbyk.course.mdp.mapper.PredictRequestModelMapper
import com.rsmbyk.course.mdp.mapper.PredictResponseModelMapper
import com.rsmbyk.course.mdp.model.PredictRequestModel
import com.rsmbyk.course.mdp.model.PredictResponseModel
import dagger.Module
import dagger.Provides

@Module
class AbsentFragmentModule {

    @Provides
    fun provideViewModel (fragment: AbsentFragment, factory: AbsentViewModelFactory): AbsentViewModel =
        ViewModelProviders.of (fragment, factory).get (AbsentViewModel::class.java)

    @Provides
    fun provideViewModelFactory (getPredictionUseCase: GetPredictionUseCase, locationClient: FusedLocationProviderClient, predictRequestMapper: Mapper<PredictRequest, PredictRequestModel>, predictResponseMapper: Mapper<PredictResponse, PredictResponseModel>): AbsentViewModelFactory =
        AbsentViewModelFactory (getPredictionUseCase, locationClient, predictRequestMapper, predictResponseMapper)

    @Provides
    fun provideGetPredictionUseCase (repository: AttendanceRepository): GetPredictionUseCase =
        GetPredictionUseCase (repository)

    @Provides
    fun provideAttendanceRepository (context: Context, studentDao: StudentDao, volleyRequestQueue: VolleyRequestQueue, studentEntityMapper: Mapper<Student, StudentEntity>, predictRequestDataMapper: Mapper<PredictRequest, PredictRequestData>, predictResponseDataMapper: Mapper<PredictResponse, PredictResponseData>): AttendanceRepository {
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
    fun providePredictResponseDataMapper (): Mapper<PredictResponse, PredictResponseData> =
        PredictResponseDataMapper ()

    @Provides
    fun providePredictRequestModelMapper (): Mapper<PredictRequest, PredictRequestModel> =
        PredictRequestModelMapper ()

    @Provides
    fun providePredictResponseModelMapper (): Mapper<PredictResponse, PredictResponseModel> =
        PredictResponseModelMapper ()

    @Provides
    fun provideLocationClient (context: Context): FusedLocationProviderClient =
        FusedLocationProviderClient (context)

    @Provides
    fun provideUtilPermission (fragment: AbsentFragment) =
        PermissionUtil (fragment = fragment)

    @Provides
    fun provideZXHandler () =
        ZXHandler ()
}

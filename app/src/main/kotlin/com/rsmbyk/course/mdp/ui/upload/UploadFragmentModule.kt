package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.PermissionUtil
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.db.dao.UploadImageDao
import com.rsmbyk.course.mdp.data.db.entity.UploadImageEntity
import com.rsmbyk.course.mdp.data.mapper.UploadImageEntityMapper
import com.rsmbyk.course.mdp.data.mapper.UploadImageRequestDataMapper
import com.rsmbyk.course.mdp.data.mapper.UploadImageResponseDataMapper
import com.rsmbyk.course.mdp.data.model.UploadImageRequestData
import com.rsmbyk.course.mdp.data.model.UploadImageResponseData
import com.rsmbyk.course.mdp.data.repository.UploadImageDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetUploadListUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveUploadImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.mapper.UploadImageModelMapper
import com.rsmbyk.course.mdp.mapper.UploadImageResponseModelMapper
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.model.UploadImageResponseModel
import dagger.Module
import dagger.Provides

@Module
class UploadFragmentModule {

    @Provides
    fun providePermissionUtil (fragment: UploadFragment): PermissionUtil =
        PermissionUtil (fragment = fragment)

    @Provides
    fun provideUploadImageEntityMapper (): Mapper<UploadImage, UploadImageEntity> =
        UploadImageEntityMapper ()

    @Provides
    fun provideUploadImageRequestDataMapper (): Mapper<UploadImageRequest, UploadImageRequestData> =
        UploadImageRequestDataMapper ()

    @Provides
    fun provideUploadImageResponseDataMapper (): Mapper<UploadImageResponse, UploadImageResponseData> =
        UploadImageResponseDataMapper ()

    @Provides
    fun provideUploadImageRepository (context: Context, uploadImageDao: UploadImageDao, volleyRequestQueue: VolleyRequestQueue, uploadImageMapper: Mapper<UploadImage, UploadImageEntity>, uploadImageRequestMapper: Mapper<UploadImageRequest, UploadImageRequestData>, uploadImageResponseMapper: Mapper<UploadImageResponse, UploadImageResponseData>): UploadImageRepository =
        UploadImageDataRepository (
            context, uploadImageDao, volleyRequestQueue, uploadImageMapper, uploadImageRequestMapper, uploadImageResponseMapper)

    @Provides
    fun provideGetUploadListUseCase (repository: UploadImageRepository): GetUploadListUseCase =
        GetUploadListUseCase (repository)

    @Provides
    fun provideUploadImageUseCase (repository: UploadImageRepository): UploadImageUseCase =
        UploadImageUseCase (repository)

    @Provides
    fun provideSaveUploadImageUseCase (repository: UploadImageRepository): SaveUploadImageUseCase =
        SaveUploadImageUseCase (repository)

    @Provides
    fun provideUploadImageModelMapper (): Mapper<UploadImage, UploadImageModel> =
        UploadImageModelMapper ()

    @Provides
    fun provideUploadImageResponseModelMapper (): Mapper<UploadImageResponse, UploadImageResponseModel> =
        UploadImageResponseModelMapper ()

    @Provides
    fun provideNetworkingViewModelFactory (getUploadListUseCase: GetUploadListUseCase, uploadImageUseCase: UploadImageUseCase, saveUploadImageUseCase: SaveUploadImageUseCase, uploadImageMapper: Mapper<UploadImage, UploadImageModel>, uploadImageResponseMapper: Mapper<UploadImageResponse, UploadImageResponseModel>): UploadViewModelFactory =
        UploadViewModelFactory (
            getUploadListUseCase, uploadImageUseCase, saveUploadImageUseCase, uploadImageMapper, uploadImageResponseMapper)

    @Provides
    fun provideNetworkingViewModel (fragment: UploadFragment, factory: UploadViewModelFactory): UploadViewModel =
        ViewModelProviders.of (fragment, factory).get (UploadViewModel::class.java)
}

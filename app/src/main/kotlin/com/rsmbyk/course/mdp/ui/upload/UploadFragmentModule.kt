package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.util.PermissionUtil
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
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImageNamesUseCase
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
    fun provideNetworkingViewModel (fragment: UploadFragment, factory: UploadViewModelFactory): UploadViewModel =
        ViewModelProviders.of (fragment, factory).get (UploadViewModel::class.java)

    @Provides
    fun provideNetworkingViewModelFactory (getUploadImageNamesUseCase: GetUploadImageNamesUseCase, uploadImageUseCase: UploadImageUseCase, saveUploadImageUseCase: SaveUploadImageUseCase, uploadImageModelMapper: Mapper<UploadImage, UploadImageModel>, uploadImageResponseModelMapper: Mapper<UploadImageResponse, UploadImageResponseModel>): UploadViewModelFactory =
        UploadViewModelFactory (
            getUploadImageNamesUseCase, uploadImageUseCase, saveUploadImageUseCase, uploadImageModelMapper, uploadImageResponseModelMapper)

    @Provides
    fun provideGetUploadImageNamesUseCase (repository: UploadImageRepository): GetUploadImageNamesUseCase =
        GetUploadImageNamesUseCase (repository)

    @Provides
    fun provideUploadImageUseCase (repository: UploadImageRepository): UploadImageUseCase =
        UploadImageUseCase (repository)

    @Provides
    fun provideSaveUploadImageUseCase (repository: UploadImageRepository): SaveUploadImageUseCase =
        SaveUploadImageUseCase (repository)

    @Provides
    fun provideUploadImageRepository (context: Context, uploadImageDao: UploadImageDao, volleyRequestQueue: VolleyRequestQueue, uploadImageEntityMapper: Mapper<UploadImage, UploadImageEntity>, uploadImageRequestDataMapper: Mapper<UploadImageRequest, UploadImageRequestData>, uploadImageResponseDataMapper: Mapper<UploadImageResponse, UploadImageResponseData>): UploadImageRepository =
        UploadImageDataRepository (
            context, uploadImageDao, volleyRequestQueue, uploadImageEntityMapper, uploadImageRequestDataMapper, uploadImageResponseDataMapper)

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
    fun provideUploadImageModelMapper (): Mapper<UploadImage, UploadImageModel> =
        UploadImageModelMapper ()

    @Provides
    fun provideUploadImageResponseModelMapper (): Mapper<UploadImageResponse, UploadImageResponseModel> =
        UploadImageResponseModelMapper ()

    @Provides
    fun providePermissionUtil (fragment: UploadFragment): PermissionUtil =
        PermissionUtil (fragment = fragment)
}

package com.rsmbyk.course.mdp.ui.database

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.db.dao.UploadImageDao
import com.rsmbyk.course.mdp.data.db.entity.UploadImageEntity
import com.rsmbyk.course.mdp.data.mapper.SuperResponseDataMapper
import com.rsmbyk.course.mdp.data.mapper.UploadImageEntityMapper
import com.rsmbyk.course.mdp.data.mapper.UploadImageRequestDataMapper
import com.rsmbyk.course.mdp.data.model.SuperResponseData
import com.rsmbyk.course.mdp.data.model.UploadImageRequestData
import com.rsmbyk.course.mdp.data.repository.UploadImageDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImagesUseCase
import com.rsmbyk.course.mdp.mapper.UploadImageModelMapper
import com.rsmbyk.course.mdp.model.UploadImageModel
import dagger.Module
import dagger.Provides

@Module
class DatabaseFragmentModule {

    @Provides
    fun provideDatabaseViewModel (fragment: DatabaseFragment, factory: DatabaseViewModelFactory): DatabaseViewModel =
        ViewModelProviders.of (fragment, factory).get (DatabaseViewModel::class.java)

    @Provides
    fun provideDatabaseViewModelFactory (getUploadImagesUseCase: GetUploadImagesUseCase, uploadImageMapper: Mapper<UploadImage, UploadImageModel>): DatabaseViewModelFactory =
        DatabaseViewModelFactory (getUploadImagesUseCase, uploadImageMapper)

    @Provides
    fun provideGetUploadImagesUseCase (repository: UploadImageRepository): GetUploadImagesUseCase =
        GetUploadImagesUseCase (repository)

    @Provides
    fun provideUploadImageRepository (context: Context, uploadImageDao: UploadImageDao, volleyRequestQueue: VolleyRequestQueue, uploadImageEntityMapper: Mapper<UploadImage, UploadImageEntity>, uploadImageRequestDataMapper: Mapper<UploadImageRequest, UploadImageRequestData>, uploadImageResponseDataMapper: Mapper<SuperResponse, SuperResponseData>): UploadImageRepository =
        UploadImageDataRepository (
            context, uploadImageDao, volleyRequestQueue, uploadImageEntityMapper, uploadImageRequestDataMapper, uploadImageResponseDataMapper)

    @Provides
    fun provideUploadImageEntityMapper (): Mapper<UploadImage, UploadImageEntity> =
        UploadImageEntityMapper ()

    @Provides
    fun provideUploadImageRequestDataMapper (): Mapper<UploadImageRequest, UploadImageRequestData> =
        UploadImageRequestDataMapper ()

    @Provides
    fun provideUploadImageResponseDataMapper (): Mapper<SuperResponse, SuperResponseData> =
        SuperResponseDataMapper ()

    @Provides
    fun provideUploadImageModelMapper (): Mapper<UploadImage, UploadImageModel> =
        UploadImageModelMapper ()
}

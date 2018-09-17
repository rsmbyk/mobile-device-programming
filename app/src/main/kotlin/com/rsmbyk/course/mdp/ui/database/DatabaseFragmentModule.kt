package com.rsmbyk.course.mdp.ui.database

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.db.UploadImageDao
import com.rsmbyk.course.mdp.data.mapper.UploadImageDataMapper
import com.rsmbyk.course.mdp.data.model.UploadImageData
import com.rsmbyk.course.mdp.data.repository.UploadImageDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetUploadedImageUseCase
import com.rsmbyk.course.mdp.mapper.UploadImageModelMapper
import com.rsmbyk.course.mdp.model.UploadImageModel
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class DatabaseFragmentModule {

    @Provides
    fun provideUploadImageDataMapper (): Mapper<UploadImage, UploadImageData> =
        UploadImageDataMapper ()

    @Provides
    fun provideUploadImageRepository (
        context: Context,
        @Named("private_image_directory") privateImageDirectory: File,
        mapper: Mapper<UploadImage, UploadImageData>,
        uploadImageDao: UploadImageDao,
        volleyRequestQueue: VolleyRequestQueue)
            : UploadImageRepository =
        UploadImageDataRepository (
                context, privateImageDirectory, mapper, uploadImageDao, volleyRequestQueue)

    @Provides
    fun provideGetUplaodedImageUseCase (uploadImageRepository: UploadImageRepository)
            : GetUploadedImageUseCase =
        GetUploadedImageUseCase (uploadImageRepository)

    @Provides
    fun provideUploadImageModelMapper (): Mapper<UploadImage, UploadImageModel> =
        UploadImageModelMapper ()

    @Provides
    fun provideDatabaseViewModelFactory (
        mapper: Mapper<UploadImage, UploadImageModel>,
        getUploadedImageUseCase: GetUploadedImageUseCase)
            : DatabaseViewModelFactory =
        DatabaseViewModelFactory (mapper, getUploadedImageUseCase)

    @Provides
    fun provideDatabaseViewModel (fragment: DatabaseFragment, factory: DatabaseViewModelFactory)
            : DatabaseViewModel =
        ViewModelProviders.of (fragment, factory).get (DatabaseViewModel::class.java)
}

package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.repository.ImageDataRepository
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class CameraActivityModule {

    @Provides
    fun provideImageRepository (context: Context, volleyRequestQueue: VolleyRequestQueue)
            : ImageRepository =
        ImageDataRepository (context, volleyRequestQueue)

    @Provides
    fun provideGetImagesUseCase (imageRepository: ImageRepository): GetImagesUseCase =
        GetImagesUseCase (imageRepository)

    @Provides
    fun provideGetCapturedImageUseCase (imageRepository: ImageRepository): GetCapturedImageUseCase =
        GetCapturedImageUseCase (imageRepository)

    @Provides
    fun provideCameraViewModelFactory (
        @Named ("image_directory") imageDirectory: File,
        getImagesUseCase: GetImagesUseCase,
        getCapturedImageUseCase: GetCapturedImageUseCase)
            : CameraViewModelFactory =
        CameraViewModelFactory (imageDirectory, getImagesUseCase, getCapturedImageUseCase)

    @Provides
    fun provideCameraViewModel (activity: CameraActivity, factory: CameraViewModelFactory)
            : CameraViewModel =
        ViewModelProviders.of (activity, factory).get (CameraViewModel::class.java)

    @Provides
    fun provideCameraActivityModule (activity: CameraActivity): CameraUtil =
        CameraUtil (activity)
}

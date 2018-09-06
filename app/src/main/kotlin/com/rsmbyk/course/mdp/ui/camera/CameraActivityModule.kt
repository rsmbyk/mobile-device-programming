package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModelProviders
import com.rsmbyk.course.mdp.data.repository.ImageDataRepository
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import dagger.Module
import dagger.Provides

@Module
class CameraActivityModule {

    @Provides
    fun provideImageRepository (): ImageRepository =
        ImageDataRepository ()

    @Provides
    fun provideGetImagesUseCase (imageRepository: ImageRepository): GetImagesUseCase =
        GetImagesUseCase (imageRepository)

    @Provides
    fun provideGetCapturedImageUseCase (imageRepository: ImageRepository): GetCapturedImageUseCase =
        GetCapturedImageUseCase (imageRepository)

    @Provides
    fun provideCameraViewModelFactory (getImagesUseCase: GetImagesUseCase, getCapturedImageUseCase: GetCapturedImageUseCase): CameraViewModelFactory =
        CameraViewModelFactory (getImagesUseCase, getCapturedImageUseCase)

    @Provides
    fun provideCameraViewModel (activity: CameraActivity, factory: CameraViewModelFactory): CameraViewModel =
        ViewModelProviders.of (activity, factory).get (CameraViewModel::class.java)
}

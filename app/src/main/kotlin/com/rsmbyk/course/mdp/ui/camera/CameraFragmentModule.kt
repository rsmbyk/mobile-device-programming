package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModelProviders
import com.rsmbyk.course.mdp.common.CameraUtil
import com.rsmbyk.course.mdp.data.repository.ImageDataRepository
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class CameraFragmentModule {

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
    fun provideCameraViewModelFactory (
            @Named("image_directory") imageDirectory: File,
            getImagesUseCase: GetImagesUseCase,
            getCapturedImageUseCase: GetCapturedImageUseCase)
            : CameraViewModelFactory =
        CameraViewModelFactory (imageDirectory, getImagesUseCase, getCapturedImageUseCase)

    @Provides
    fun provideCameraViewModel (fragment: CameraFragment, factory: CameraViewModelFactory)
            : CameraViewModel =
        ViewModelProviders.of (fragment, factory).get (CameraViewModel::class.java)

    @Provides
    fun provideCameraActivityModule (fragment: CameraFragment): CameraUtil =
        CameraUtil (fragment)
}

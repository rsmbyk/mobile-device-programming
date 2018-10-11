package com.rsmbyk.course.mdp.ui.gallery

import android.arch.lifecycle.ViewModelProviders
import com.rsmbyk.course.mdp.common.PermissionUtil
import com.rsmbyk.course.mdp.data.repository.ImageDataRepository
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveImageUseCase
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named

@Module
class GalleryFragmentModule {

    @Provides
    fun providePermissionUtil (fragment: GalleryFragment): PermissionUtil =
        PermissionUtil (fragment = fragment)

    @Provides
    fun provideImageRepository (@Named ("image_directory") directory: File): ImageRepository =
        ImageDataRepository (directory)

    @Provides
    fun provideGetImagesUseCase (repository: ImageRepository): GetImagesUseCase =
        GetImagesUseCase (repository)

    @Provides
    fun provideSaveImageUseCase (repository: ImageRepository): SaveImageUseCase =
        SaveImageUseCase (repository)

    @Provides
    fun provideCameraViewModelFactory (getImagesUseCase: GetImagesUseCase, saveImageUseCase: SaveImageUseCase): GalleryViewModelFactory =
        GalleryViewModelFactory (getImagesUseCase, saveImageUseCase)

    @Provides
    fun provideGalleryViewModel (fragment: GalleryFragment, factory: GalleryViewModelFactory): GalleryViewModel =
        ViewModelProviders.of (fragment, factory).get (GalleryViewModel::class.java)
}

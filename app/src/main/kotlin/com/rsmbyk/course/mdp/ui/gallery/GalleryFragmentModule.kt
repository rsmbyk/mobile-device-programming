package com.rsmbyk.course.mdp.ui.gallery

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.data.repository.GalleryDataRepository
import com.rsmbyk.course.mdp.domain.repository.GalleryRepository
import com.rsmbyk.course.mdp.domain.usecase.GetGalleryImagesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveGalleryImageUseCase
import dagger.Module
import dagger.Provides

@Module
class GalleryFragmentModule {

    @Provides
    fun provideGalleryViewModel (fragment: GalleryFragment, factory: GalleryViewModelFactory): GalleryViewModel =
        ViewModelProviders.of (fragment, factory).get (GalleryViewModel::class.java)

    @Provides
    fun provideCameraViewModelFactory (getGalleryImagesUseCase: GetGalleryImagesUseCase, saveGalleryImageUseCase: SaveGalleryImageUseCase): GalleryViewModelFactory =
        GalleryViewModelFactory (getGalleryImagesUseCase, saveGalleryImageUseCase)

    @Provides
    fun provideGetImagesUseCase (repository: GalleryRepository): GetGalleryImagesUseCase =
        GetGalleryImagesUseCase (repository)

    @Provides
    fun provideSaveImageUseCase (repository: GalleryRepository): SaveGalleryImageUseCase =
        SaveGalleryImageUseCase (repository)

    @Provides
    fun provideImageRepository (context: Context): GalleryRepository =
        GalleryDataRepository (context)

    @Provides
    fun providePermissionUtil (fragment: GalleryFragment): PermissionUtil =
        PermissionUtil (fragment = fragment)
}

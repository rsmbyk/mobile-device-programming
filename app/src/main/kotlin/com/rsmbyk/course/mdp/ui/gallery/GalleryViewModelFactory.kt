package com.rsmbyk.course.mdp.ui.gallery

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetGalleryImagesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveGalleryImageUseCase

class GalleryViewModelFactory (
    private val getGalleryImagesUseCase: GetGalleryImagesUseCase,
    private val saveGalleryImageUseCase: SaveGalleryImageUseCase)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        GalleryViewModel (getGalleryImagesUseCase, saveGalleryImageUseCase) as T
}

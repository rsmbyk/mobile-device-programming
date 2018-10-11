package com.rsmbyk.course.mdp.ui.gallery

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveImageUseCase

class GalleryViewModelFactory (
    private val getImagesUseCase: GetImagesUseCase,
    private val saveImageUseCase: SaveImageUseCase)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        GalleryViewModel (getImagesUseCase, saveImageUseCase) as T
}

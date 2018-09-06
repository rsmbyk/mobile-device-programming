package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase

class CameraViewModelFactory (private val getImagesUseCase: GetImagesUseCase, private val getCapturedImageUseCase: GetCapturedImageUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        CameraViewModel (getImagesUseCase, getCapturedImageUseCase) as T
}

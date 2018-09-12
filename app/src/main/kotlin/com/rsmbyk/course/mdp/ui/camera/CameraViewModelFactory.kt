package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import java.io.File

class CameraViewModelFactory (
    private val imageDirectory: File,
    private val getImagesUseCase: GetImagesUseCase,
    private val getCapturedImageUseCase: GetCapturedImageUseCase)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        CameraViewModel (imageDirectory, getImagesUseCase, getCapturedImageUseCase) as T
}

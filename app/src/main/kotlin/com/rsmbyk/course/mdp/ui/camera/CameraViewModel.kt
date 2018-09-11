package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import java.io.File

class CameraViewModel (
    private val getImagesUseCase: GetImagesUseCase,
    private val getCapturedImageUseCase: GetCapturedImageUseCase): ViewModel () {

    fun getImages (): List<File> =
        getImagesUseCase ()

    fun getCapturedImage (): File =
        getCapturedImageUseCase ()
}

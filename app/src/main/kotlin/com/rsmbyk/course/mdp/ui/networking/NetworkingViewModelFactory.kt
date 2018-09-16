package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import java.io.File

class NetworkingViewModelFactory (
    private val nrp: String,
    private val uploadImageDirectory: File,
    private val mapper: Mapper<UploadImage, UploadImageModel>,
    private val getCapturedImageUseCase: GetCapturedImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        NetworkingViewModel (
            nrp, uploadImageDirectory, mapper, getCapturedImageUseCase, uploadImageUseCase) as T
}

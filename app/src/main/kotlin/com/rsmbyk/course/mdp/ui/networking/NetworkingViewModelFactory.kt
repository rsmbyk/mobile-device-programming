package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import java.io.File

class NetworkingViewModelFactory (
    private val nrp: String,
    private val uploadImageDirectory: File,
    private val getCapturedImageUseCase: GetCapturedImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        NetworkingViewModel (nrp, uploadImageDirectory, getCapturedImageUseCase, uploadImageUseCase) as T
}

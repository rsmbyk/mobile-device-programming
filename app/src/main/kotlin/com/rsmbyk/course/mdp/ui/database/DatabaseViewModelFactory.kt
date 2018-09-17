package com.rsmbyk.course.mdp.ui.database

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.usecase.GetUploadedImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel

class DatabaseViewModelFactory (
    private val mapper: Mapper<UploadImage, UploadImageModel>,
    private val getUploadedImageUseCase: GetUploadedImageUseCase)
        : ViewModelProvider.Factory {

    override fun <T: ViewModel> create (modelClass: Class<T>): T =
        DatabaseViewModel (mapper, getUploadedImageUseCase) as T
}

package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImageNamesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveUploadImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.model.UploadImageResponseModel

class UploadViewModelFactory (
    private val getUploadImageNamesUseCase: GetUploadImageNamesUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val saveUploadImageUseCase: SaveUploadImageUseCase,
    private val uploadImageMapper: Mapper<UploadImage, UploadImageModel>,
    private val uploadImageResponseMapper: Mapper<UploadImageResponse, UploadImageResponseModel>)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        UploadViewModel (
            getUploadImageNamesUseCase,
            uploadImageUseCase,
            saveUploadImageUseCase,
            uploadImageMapper,
            uploadImageResponseMapper) as T
}

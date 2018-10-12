package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetUploadListUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveUploadImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.model.UploadImageResponseModel

class UploadViewModelFactory (
    private val getUploadListUseCase: GetUploadListUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val saveUploadImageUseCase: SaveUploadImageUseCase,
    private val uploadImageModelMapper: Mapper<UploadImage, UploadImageModel>,
    private val uploadImageResponseModelMapper: Mapper<UploadImageResponse, UploadImageResponseModel>)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        UploadViewModel (
            getUploadListUseCase,
            uploadImageUseCase,
            saveUploadImageUseCase,
            uploadImageModelMapper,
            uploadImageResponseModelMapper) as T
}

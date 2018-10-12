package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository

class SaveUploadImageUseCase (private val repository: UploadImageRepository) {

    operator fun invoke (uploadImage: UploadImage) =
        repository.saveUploadImage (uploadImage)
}

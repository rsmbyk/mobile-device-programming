package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.common.VolleyRequestCallback
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

class UploadImageUseCase (private val imageRepository: ImageRepository) {

    operator fun invoke (
            url: String,
            nrp: String,
            imageFile: File,
            callback: VolleyRequestCallback<String>) =
        imageRepository.uploadImage (url, nrp, imageFile, callback)
}

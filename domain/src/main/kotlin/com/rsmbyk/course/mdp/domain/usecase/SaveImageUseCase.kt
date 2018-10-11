package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.ImageRepository

class SaveImageUseCase (private val repository: ImageRepository) {

    operator fun invoke (image: ByteArray) =
        repository.saveImage (image)
}

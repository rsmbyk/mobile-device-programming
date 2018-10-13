package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.GalleryRepository

class SaveGalleryImageUseCase (private val repository: GalleryRepository) {

    operator fun invoke (image: ByteArray) =
        repository.saveImage (image)
}

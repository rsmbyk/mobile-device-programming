package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

class GetImagesUseCase (private val imageRepository: ImageRepository) {

    operator fun invoke (directory: File): List<File> =
        imageRepository.getImages (directory)
}

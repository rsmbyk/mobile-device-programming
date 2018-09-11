package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

class GetCapturedImageUseCase (private val imageRepository: ImageRepository) {

    operator fun invoke (): File =
        imageRepository.getImages ().first ()
}

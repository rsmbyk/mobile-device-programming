package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

class GetImagesUseCase (private val repository: ImageRepository) {

    operator fun invoke (): List<File> =
        repository.getImages ()
}

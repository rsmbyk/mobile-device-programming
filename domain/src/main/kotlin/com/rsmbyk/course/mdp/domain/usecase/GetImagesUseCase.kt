package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import io.reactivex.Single

class GetImagesUseCase (private val imageRepository: ImageRepository) {

    operator fun invoke (): Single<List<ByteArray>> =
        imageRepository.getImages ()
}

package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.GalleryRepository
import io.reactivex.Single

class GetGalleryImagesUseCase (private val repository: GalleryRepository) {

    operator fun invoke (): Single<List<ByteArray>> =
        repository.getImages ()
}

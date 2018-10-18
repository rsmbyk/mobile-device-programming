package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Maybe

class GetUploadImagesUseCase (private val repository: UploadImageRepository) {

    operator fun invoke (): Maybe<List<UploadImage>> =
        repository.getImages ()
}

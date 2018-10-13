package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Single

class GetUploadImagesUseCase (private val repository: UploadImageRepository) {

    operator fun invoke (): Single<List<UploadImage>> =
        repository.getImages ()

}

package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Maybe
import io.reactivex.Single

class GetUploadedImageUseCase (private val uploadImageRepository: UploadImageRepository) {

    operator fun invoke (): Single<List<UploadImage>> =
        uploadImageRepository.getUploadedImages ()

}

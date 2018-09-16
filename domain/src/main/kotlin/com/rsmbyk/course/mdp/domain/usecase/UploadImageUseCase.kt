package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Observable

class UploadImageUseCase (private val uploadImageRepository: UploadImageRepository) {

    operator fun invoke (uploadImageRequest: UploadImageRequest): Observable<UploadImage> =
        uploadImageRepository.uploadImage (uploadImageRequest)
}

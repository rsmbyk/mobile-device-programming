package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository

class UploadImageUseCase (private val uploadImageRepository: UploadImageRepository) {

    operator fun invoke (uploadImageRequest: UploadImageRequest, callback: UploadImageCallback) =
        uploadImageRepository.uploadImage (uploadImageRequest, callback)

//    operator fun invoke (uploadImageRequest: UploadImageRequest): Observable<UploadImageResponse> =
//        uploadImageRepository.uploadImage (uploadImageRequest)
}

package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository

class UploadImageUseCase (private val uploadImageRepository: UploadImageRepository) {

    operator fun invoke (requestCode: Int, uploadImageRequest: UploadImageRequest, callback: UploadImageCallback) =
        uploadImageRepository.uploadImage (requestCode, uploadImageRequest, callback)

//    operator fun invoke (uploadImageRequest: UploadImageRequest): Observable<UploadImageResponse> =
//        uploadImageRepository.uploadImage (uploadImageRequest)
}

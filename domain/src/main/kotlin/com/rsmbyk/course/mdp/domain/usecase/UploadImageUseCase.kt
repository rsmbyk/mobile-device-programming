package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Single

class UploadImageUseCase (private val repository: UploadImageRepository) {

    operator fun invoke (request: UploadImageRequest): Single<SuperResponse> =
        repository.uploadImage (request)
}

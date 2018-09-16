package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import io.reactivex.Observable

interface UploadImageRepository {

    fun uploadImage (uploadImageRequest: UploadImageRequest): Observable<UploadImageResponse>
}

package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest

interface UploadImageRepository {

    fun uploadImage (uploadImageRequest: UploadImageRequest, callback: UploadImageCallback)
//    fun uploadImage (uploadImageRequest: UploadImageRequest): Observable<UploadImageResponse>
}

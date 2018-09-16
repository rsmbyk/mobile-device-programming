package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import io.reactivex.Observable

interface UploadImageRepository {

    fun uploadImage (uploadImageRequest: UploadImageRequest): Observable<UploadImage>
}

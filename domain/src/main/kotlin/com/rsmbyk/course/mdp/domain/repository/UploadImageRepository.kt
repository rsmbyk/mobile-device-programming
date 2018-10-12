package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import io.reactivex.Single

interface UploadImageRepository {

    fun getUploadList (): List<String>
    fun uploadImage (request: UploadImageRequest): Single<UploadImageResponse>
    fun saveUploadImage (uploadImage: UploadImage)
    fun getUploadImages (): Single<List<UploadImage>>
}

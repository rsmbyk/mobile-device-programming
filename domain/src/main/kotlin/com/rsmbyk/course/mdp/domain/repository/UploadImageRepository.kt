package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import io.reactivex.Maybe
import io.reactivex.Single

interface UploadImageRepository {

    fun getNames (): List<String>
    fun uploadImage (request: UploadImageRequest): Single<UploadImageResponse>
    fun saveImage (uploadImage: UploadImage)
    fun getImages (): Maybe<List<UploadImage>>
}

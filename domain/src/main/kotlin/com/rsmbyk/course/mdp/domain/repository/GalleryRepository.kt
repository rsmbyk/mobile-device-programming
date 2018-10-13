package com.rsmbyk.course.mdp.domain.repository

import io.reactivex.Single

interface GalleryRepository {

    fun getImages (): Single<List<ByteArray>>
    fun saveImage (image: ByteArray)
}

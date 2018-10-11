package com.rsmbyk.course.mdp.domain.repository

import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

interface ImageRepository {

    fun getImages (directory: File): Observable<List<File>>
    fun getImages (): Single<List<ByteArray>>
    fun saveImage (image: ByteArray)
}

package com.rsmbyk.course.mdp.domain.repository

import io.reactivex.Observable
import java.io.File

interface ImageRepository {

    fun getImages (directory: File): Observable<List<File>>
    fun getCapturedImage (directory: File): Observable<File>
}

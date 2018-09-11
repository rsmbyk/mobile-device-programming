package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.common.VolleyRequestCallback
import java.io.File

interface ImageRepository {

    fun getImages (directory: File): List<File>
    fun uploadImage (
        url: String,
        nrp: String,
        imageFile: File,
        callback: VolleyRequestCallback<String>)
}

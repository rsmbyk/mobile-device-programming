package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.api.volley.request.UploadSingleImageRequest
import com.rsmbyk.course.mdp.domain.common.VolleyRequestCallback
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

private fun String.endsWithIgnoreCase (suffix: String) =
    endsWith (".$suffix", true)

class ImageDataRepository (private val context: Context, private val volleyRequestQueue: VolleyRequestQueue)
    : ImageRepository {

    private val imageFileExtensions = arrayOf ("jpg", "jpeg", "png", "gif")

    override fun getImages (directory: File): List<File> =
        directory
            .listFiles (::isImageFile)
            .sortedByDescending (File::lastModified)

    private fun isImageFile (dir: File, name: String): Boolean =
        imageFileExtensions.any (name::endsWithIgnoreCase)

    override fun uploadImage (
            url: String,
            nrp: String,
            imageFile: File,
            callback: VolleyRequestCallback<String>) {
        val request = UploadSingleImageRequest (context, url, nrp, imageFile, callback)
        volleyRequestQueue.add (request)
    }
}

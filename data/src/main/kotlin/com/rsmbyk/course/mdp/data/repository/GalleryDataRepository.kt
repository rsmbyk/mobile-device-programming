package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import android.os.Environment
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.common.isImageFile
import com.rsmbyk.course.mdp.domain.repository.GalleryRepository
import io.reactivex.Single
import java.io.File
import java.util.*

class GalleryDataRepository (private val context: Context): GalleryRepository {

    private val galleryDirectory: File = File (
        Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
        context.getString (R.string.gallery_image_directory)).apply {
            if (exists () && isFile)
                deleteRecursively ()
            if (!exists ())
                mkdirs ()
    }

    override fun getImages (): Single<List<ByteArray>> {
        return Single.create {
            try {
                it.onSuccess (
                    galleryDirectory
                        .listFiles (File::isImageFile)
                        .sortedByDescending (File::lastModified)
                        .map (File::readBytes))
            } catch (e: Exception) {
                it.onError (e)
            }
        }
    }

    override fun saveImage (image: ByteArray) {
        File (
            galleryDirectory.path,
            UUID.nameUUIDFromBytes (image).toString () + context.getString (R.string.gallery_image_file_suffix))
                .writeBytes (image)
    }
}

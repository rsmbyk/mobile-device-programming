package com.rsmbyk.course.mdp.data.repository

import com.rsmbyk.course.mdp.data.common.isImageFile
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File
import java.util.*

class ImageDataRepository (private val directory: File): ImageRepository {

    override fun getImages (directory: File): Observable<List<File>> {
        return Observable.create {
            try {
                it.onNext (getListFiles ())
            } catch (e: Exception) {
                it.onError (e)
            } finally {
                it.onComplete ()
            }
        }
    }

    override fun getImages (): Single<List<ByteArray>> {
        return Single.create {
            try {
                it.onSuccess (getListFiles ().map (File::readBytes))
            } catch (e: Exception) {
                it.onError (e)
            }
        }
    }

    override fun saveImage (image: ByteArray) {
        File (directory.path, UUID.nameUUIDFromBytes (image).toString () + ".jpeg")
            .writeBytes (image)
    }

    private fun getListFiles (): List<File> =
        directory
            .listFiles (File::isImageFile)
            .sortedByDescending (File::lastModified)
}

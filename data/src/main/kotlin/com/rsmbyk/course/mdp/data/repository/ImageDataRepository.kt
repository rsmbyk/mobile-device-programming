package com.rsmbyk.course.mdp.data.repository

import com.rsmbyk.course.mdp.data.common.isImageFile
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import io.reactivex.Observable
import java.io.File

class ImageDataRepository: ImageRepository {

    override fun getImages (directory: File): Observable<List<File>> {
        return Observable.create {
            try {
                it.onNext (getListFiles (directory))
            } catch (e: Exception) {
                it.onError (e)
            } finally {
                it.onComplete ()
            }
        }
    }

    override fun getCapturedImage (directory: File): Observable<File> {
        return Observable.create {
            try {
                it.onNext (getListFiles (directory).first ())
            } catch (e: Exception) {
                it.onError (e)
            } finally {
                it.onComplete ()
            }
        }
    }

    private fun getListFiles (directory: File): List<File> =
        directory
            .listFiles (File::isImageFile)
            .sortedByDescending (File::lastModified)
}

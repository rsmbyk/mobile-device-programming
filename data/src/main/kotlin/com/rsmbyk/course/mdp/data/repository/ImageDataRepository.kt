package com.rsmbyk.course.mdp.data.repository

import com.rsmbyk.course.mdp.data.common.isImageFile
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

class ImageDataRepository: ImageRepository {

    override fun getImages (directory: File): List<File> =
        directory
            .listFiles (File::isImageFile)
            .sortedByDescending (File::lastModified)
}

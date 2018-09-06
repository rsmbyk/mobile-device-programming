package com.rsmbyk.course.mdp.data.repository

import android.os.Environment
import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import java.io.File

class ImageDataRepository: ImageRepository {

    override fun getImages (): List<File> =
        File (Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES), "MDP").let {
            if (!it.exists ())
                it.mkdirs ()
            it
                .listFiles ()
                .sortedByDescending (File::lastModified)
        }
}

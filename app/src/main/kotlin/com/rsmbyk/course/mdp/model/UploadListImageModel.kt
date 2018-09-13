package com.rsmbyk.course.mdp.model

import java.io.File

data class UploadListImageModel (
    val file: File,
    var uploadProgress: UploadProgress = UploadProgress.IDLE) {

    enum class UploadProgress {
        IDLE,
        UPLOADING,
        FAILED,
        SUCCESS
    }
}

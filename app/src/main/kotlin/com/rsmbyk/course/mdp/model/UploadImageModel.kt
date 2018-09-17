package com.rsmbyk.course.mdp.model

import java.io.File

data class UploadImageModel (
    val index: Int,
    val file: File,
    val elapsedTime: Float = -1f,
    val uploadTime: Long = -1,
    var uploadProgress: UploadProgress = UploadProgress.IDLE
) {

    enum class UploadProgress {
        IDLE,
        UPLOADING,
        FAILED,
        SUCCESS
    }
}

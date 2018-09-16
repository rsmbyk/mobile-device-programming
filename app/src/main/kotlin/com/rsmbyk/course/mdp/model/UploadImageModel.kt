package com.rsmbyk.course.mdp.model

import java.io.File

data class UploadImageModel (
    val index: Int,
    val file: File,
    val elapsedTime: Float = 0f,
    val uploadTime: Long = 0,
    var uploadProgress: UploadProgress = UploadProgress.IDLE
) {

    enum class UploadProgress {
        IDLE,
        UPLOADING,
        FAILED,
        SUCCESS
    }
}

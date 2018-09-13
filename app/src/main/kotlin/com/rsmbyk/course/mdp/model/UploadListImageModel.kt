package com.rsmbyk.course.mdp.model

import java.io.File

data class UploadListImageModel (
    val file: File,
    var isUploaded: Boolean = false
)

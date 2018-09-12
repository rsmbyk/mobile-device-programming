package com.rsmbyk.course.mdp.model

import java.io.File

data class UploadImageModel (
    val file: File,
    val uploaded: Boolean = false
)

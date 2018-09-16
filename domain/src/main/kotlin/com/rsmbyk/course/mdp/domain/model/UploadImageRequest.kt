package com.rsmbyk.course.mdp.domain.model

import java.io.File

data class UploadImageRequest (
    val requestCode: Int,
    val nrp: String,
    val image: File
)

package com.rsmbyk.course.mdp.domain.model

data class UploadImage (
    val code: Int,
    val path: String,
    val elapsedTime: Float,
    val uploadTime: Long
): Entity

package com.rsmbyk.course.mdp.domain.model

class UploadImage (
    val id: Int,
    val name: String,
    val image: ByteArray,
    val elapsedTime: Float,
    val timestamp: Long)
        : Entity

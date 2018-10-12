package com.rsmbyk.course.mdp.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity (tableName = "images")
class UploadImageEntity (

    @PrimaryKey
    val id: Int,
    val name: String,
    val image: ByteArray,
    val elapsedTime: Float,
    val timestamp: Long
)

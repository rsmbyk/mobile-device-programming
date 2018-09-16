package com.rsmbyk.course.mdp.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity (tableName = "upload_images")
data class UploadImageData (

    @PrimaryKey
    val id: Int,

    val path: String,

    @ColumnInfo (name = "elapsed_time")
    val elapsedTime: Float,

    @ColumnInfo (name = "upload_time")
    val uploadTime: Long
)

package com.rsmbyk.course.mdp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rsmbyk.course.mdp.data.model.UploadImageData

@Database (entities = [UploadImageData::class], version = 1)
abstract class MobileDatabase: RoomDatabase () {

    abstract fun uploadImageDao (): UploadImageDao
}

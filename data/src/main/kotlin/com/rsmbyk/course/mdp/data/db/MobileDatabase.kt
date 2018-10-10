package com.rsmbyk.course.mdp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.rsmbyk.course.mdp.data.db.converter.AttendanceConverter
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import com.rsmbyk.course.mdp.data.model.UploadImageData

@Database (version = 2, entities = [
    UploadImageData::class,
    StudentEntity::class])
@TypeConverters (AttendanceConverter::class)
abstract class MobileDatabase: RoomDatabase () {

    abstract fun uploadImageDao (): UploadImageDao
    abstract fun studentDao (): StudentDao
}

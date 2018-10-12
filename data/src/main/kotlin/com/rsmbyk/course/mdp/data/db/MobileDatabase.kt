package com.rsmbyk.course.mdp.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.rsmbyk.course.mdp.data.db.converter.AttendanceConverter
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.dao.UploadImageDao
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import com.rsmbyk.course.mdp.data.db.entity.UploadImageEntity
import com.rsmbyk.course.mdp.data.db.seeder.StudentSeeder
import java.util.concurrent.Executors

@Database (version = 2, entities = [
    UploadImageEntity::class,
    StudentEntity::class])
@TypeConverters (AttendanceConverter::class)
abstract class MobileDatabase: RoomDatabase () {

    companion object {
        private var INSTANCE: MobileDatabase? = null
        private const val DATABASE_NAME = "mobile-db"

        fun getInstance (context: Context): MobileDatabase {
            if (INSTANCE == null)
                INSTANCE = buildDatabase (context)
            return INSTANCE!!
        }

        private fun buildDatabase (context: Context): MobileDatabase {
            return Room.databaseBuilder (context, MobileDatabase::class.java, DATABASE_NAME).run {
                addCallback (object : RoomDatabase.Callback () {
                    override fun onCreate (db: SupportSQLiteDatabase) =
                        onDatabaseCreated (context)
                })
                build ()
            }
        }

        private fun onDatabaseCreated (context: Context) {
            Executors.newSingleThreadExecutor ().execute {
                StudentSeeder ().seed (context, getInstance (context).studentDao ())
            }
        }
    }

    abstract fun uploadImageDao (): UploadImageDao
    abstract fun studentDao (): StudentDao
}

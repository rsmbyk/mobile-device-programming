package com.rsmbyk.course.mdp.di

import android.content.Context
import com.rsmbyk.course.mdp.data.db.MobileDatabase
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.dao.UploadImageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMobileDatabase (context: Context): MobileDatabase =
        MobileDatabase.getInstance (context)

    @Provides
    @Singleton
    fun provideUploadImageDao (database: MobileDatabase): UploadImageDao =
        database.uploadImageDao ()

    @Provides
    @Singleton
    fun provideStudentDao (database: MobileDatabase): StudentDao =
        database.studentDao ()
}

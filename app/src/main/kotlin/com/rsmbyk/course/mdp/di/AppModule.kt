package com.rsmbyk.course.mdp.di

import android.content.Context
import android.os.Environment
import com.rsmbyk.course.mdp.App
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.createNewDirectory
import com.rsmbyk.course.mdp.common.deleteFileOnExit
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext (app: App): Context =
        app

    @Provides
    @Named ("image_directory")
    fun provideImageDirectory (context: Context): File =
        File (
            Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_PICTURES),
            context.getString (R.string.image_directory))
                .createNewDirectory ()

    @Provides
    @Named ("upload_image_directory")
    fun provideOutputImageDirectory (context: Context, @Named ("image_directory") imageDirectory: File): File =
        File (imageDirectory.path, context.getString (R.string.upload_image_directory))
            .deleteFileOnExit ()
            .createNewDirectory ()
}

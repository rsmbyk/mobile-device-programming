package com.rsmbyk.course.mdp.di

import android.content.Context
import android.os.Environment
import com.rsmbyk.course.mdp.App
import com.rsmbyk.course.mdp.R
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

private fun File.deleteFileOnExit (): File {
    deleteOnExit ()
    return this
}

private fun File.createNewDirectory (): File {
    if (exists () && isFile)
        deleteRecursively ()
    if (!exists ())
        mkdirs ()
    return this
}

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

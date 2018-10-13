package com.rsmbyk.course.mdp.di

import android.content.Context
import com.rsmbyk.course.mdp.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext (app: App): Context =
        app
}

package com.rsmbyk.course.mdp.di

import android.content.Context
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRequestQueue (context: Context): VolleyRequestQueue =
        VolleyRequestQueue (context)
}

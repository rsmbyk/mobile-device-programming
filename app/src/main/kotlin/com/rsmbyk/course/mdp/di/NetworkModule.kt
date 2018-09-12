package com.rsmbyk.course.mdp.di

import android.content.Context
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @Named ("url_endpoint")
    fun provideUrlEndpoint (context: Context): String =
        context.getString (R.string.url_endpoint)

    @Provides
    @Singleton
    fun provideRequestQueue (context: Context): VolleyRequestQueue =
        VolleyRequestQueue (context)
}

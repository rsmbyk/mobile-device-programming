package com.rsmbyk.course.mdp

import com.rsmbyk.course.mdp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasActivityInjector

class App : DaggerApplication (), HasActivityInjector {

    override fun applicationInjector (): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder ().create (this)
}

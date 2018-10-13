package com.rsmbyk.course.mdp.di

import com.rsmbyk.course.mdp.ui.main.MainActivity
import com.rsmbyk.course.mdp.ui.main.MainActivityModule
import com.rsmbyk.course.mdp.ui.main.MainFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector (modules = [MainFragmentBuilder::class, MainActivityModule::class])
    abstract fun bindMainActivity (): MainActivity
}

package com.rsmbyk.course.mdp.ui.main

import com.rsmbyk.course.mdp.common.handler.DoubleTapExitHandler
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideDoubleTapExitHandler (activity: MainActivity): DoubleTapExitHandler =
        DoubleTapExitHandler (activity)
}

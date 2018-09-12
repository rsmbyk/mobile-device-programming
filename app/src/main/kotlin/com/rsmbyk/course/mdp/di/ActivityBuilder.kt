package com.rsmbyk.course.mdp.di

import com.rsmbyk.course.mdp.ui.calculator.CalculatorActivity
import com.rsmbyk.course.mdp.ui.calculator.CalculatorActivityModule
import com.rsmbyk.course.mdp.ui.camera.CameraActivity
import com.rsmbyk.course.mdp.ui.camera.CameraActivityModule
import com.rsmbyk.course.mdp.ui.menu.MenuActivity
import com.rsmbyk.course.mdp.ui.menu.MenuActivityModule
import com.rsmbyk.course.mdp.ui.networking.NetworkingActivity
import com.rsmbyk.course.mdp.ui.networking.NetworkingActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector (modules = [MenuActivityModule::class])
    abstract fun bindMainActivity (): MenuActivity

    @ContributesAndroidInjector (modules = [CalculatorActivityModule::class])
    abstract fun bindCalculatorActivity (): CalculatorActivity

    @ContributesAndroidInjector (modules = [CameraActivityModule::class])
    abstract fun bindCameraActivity (): CameraActivity

    @ContributesAndroidInjector (modules = [NetworkingActivityModule::class])
    abstract fun bindNetworkingActivity (): NetworkingActivity
}

package com.rsmbyk.course.mdp.ui.main

import com.rsmbyk.course.mdp.ui.attendance.AttendanceFragment
import com.rsmbyk.course.mdp.ui.attendance.AttendanceFragmentModule
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragment
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragmentModule
import com.rsmbyk.course.mdp.ui.camera.CameraFragment
import com.rsmbyk.course.mdp.ui.camera.CameraFragmentModule
import com.rsmbyk.course.mdp.ui.database.DatabaseFragment
import com.rsmbyk.course.mdp.ui.database.DatabaseFragmentModule
import com.rsmbyk.course.mdp.ui.menu.MenuFragment
import com.rsmbyk.course.mdp.ui.menu.MenuFragmentModule
import com.rsmbyk.course.mdp.ui.networking.NetworkingFragment
import com.rsmbyk.course.mdp.ui.networking.NetworkingFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector (modules = [MenuFragmentModule::class])
    abstract fun bindMenuFragment (): MenuFragment

    @ContributesAndroidInjector (modules = [CalculatorFragmentModule::class])
    abstract fun bindCalculatorFragment (): CalculatorFragment

    @ContributesAndroidInjector (modules = [CameraFragmentModule::class])
    abstract fun bindCameraFragment (): CameraFragment

    @ContributesAndroidInjector (modules = [NetworkingFragmentModule::class])
    abstract fun bindNetworkingFragment (): NetworkingFragment

    @ContributesAndroidInjector (modules = [DatabaseFragmentModule::class])
    abstract fun bindDatabaseFragment (): DatabaseFragment

    @ContributesAndroidInjector (modules = [AttendanceFragmentModule::class])
    abstract fun bindAttendanceFragment (): AttendanceFragment
}

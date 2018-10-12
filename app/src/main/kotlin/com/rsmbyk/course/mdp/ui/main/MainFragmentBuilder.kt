package com.rsmbyk.course.mdp.ui.main

import com.rsmbyk.course.mdp.ui.attendance.AttendanceFragment
import com.rsmbyk.course.mdp.ui.attendance.AttendanceFragmentModule
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragment
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragmentModule
import com.rsmbyk.course.mdp.ui.database.DatabaseFragment
import com.rsmbyk.course.mdp.ui.database.DatabaseFragmentModule
import com.rsmbyk.course.mdp.ui.gallery.GalleryFragment
import com.rsmbyk.course.mdp.ui.gallery.GalleryFragmentModule
import com.rsmbyk.course.mdp.ui.upload.UploadFragment
import com.rsmbyk.course.mdp.ui.upload.UploadFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector (modules = [CalculatorFragmentModule::class])
    abstract fun bindCalculatorFragment (): CalculatorFragment

    @ContributesAndroidInjector (modules = [GalleryFragmentModule::class])
    abstract fun bindGalleryFragment (): GalleryFragment

    @ContributesAndroidInjector (modules = [UploadFragmentModule::class])
    abstract fun bindUploadFragment (): UploadFragment

    @ContributesAndroidInjector (modules = [DatabaseFragmentModule::class])
    abstract fun bindDatabaseFragment (): DatabaseFragment

    @ContributesAndroidInjector (modules = [AttendanceFragmentModule::class])
    abstract fun bindAttendanceFragment (): AttendanceFragment
}

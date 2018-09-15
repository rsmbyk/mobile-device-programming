package com.rsmbyk.course.mdp.ui.menu

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.rsmbyk.course.mdp.data.repository.MenuDataRepository
import com.rsmbyk.course.mdp.domain.repository.MenuRepository
import com.rsmbyk.course.mdp.domain.usecase.GetMenusUseCase
import dagger.Module
import dagger.Provides

@Module
class MenuFragmentModule {

    @Provides
    fun provideMenuRepository (context: Context): MenuRepository =
        MenuDataRepository (context)

    @Provides
    fun provideGetMenusUseCase (menuRepository: MenuRepository): GetMenusUseCase =
        GetMenusUseCase (menuRepository)

    @Provides
    fun provideMenuViewModelFactory (getMenusUseCase: GetMenusUseCase): MenuViewModelFactory =
        MenuViewModelFactory (getMenusUseCase)

    @Provides
    fun provideMenuViewModel (fragment: MenuFragment, factory: MenuViewModelFactory): MenuViewModel =
        ViewModelProviders.of (fragment, factory).get (MenuViewModel::class.java)
}
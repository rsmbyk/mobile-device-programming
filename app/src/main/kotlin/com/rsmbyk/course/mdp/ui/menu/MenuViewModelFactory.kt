package com.rsmbyk.course.mdp.ui.menu

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetMenusUseCase

class MenuViewModelFactory (private val getMenusUseCase: GetMenusUseCase): ViewModelProvider.Factory {

    override fun <T: ViewModel> create (modelClass: Class<T>): T =
        MenuViewModel (getMenusUseCase) as T
}

package com.rsmbyk.course.mdp.ui.menu

import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetMenusUseCase

class MenuViewModel (private val getMenusUseCase: GetMenusUseCase): ViewModel () {

    fun getMenus (): List<String> =
        getMenusUseCase.execute ()
}

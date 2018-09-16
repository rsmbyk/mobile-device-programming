package com.rsmbyk.course.mdp.ui.menu

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetMenusUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MenuViewModel (private val getMenusUseCase: GetMenusUseCase): ViewModel () {

    val menus = MutableLiveData<List<String>> ()

    fun getMenus () {
        getMenusUseCase ()
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (menus::setValue)
    }
}

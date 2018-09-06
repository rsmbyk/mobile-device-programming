package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.MenuRepository

class GetMenusUseCase (private val repository: MenuRepository) {

    fun execute (): List<String> =
        repository.menus ()
}

package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.MenuRepository

class GetMenusUseCase (private val menuRepository: MenuRepository) {

    operator fun invoke (): List<String> =
        menuRepository.menus ()
}

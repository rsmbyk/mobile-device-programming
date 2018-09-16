package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.MenuRepository
import io.reactivex.Observable

class GetMenusUseCase (private val menuRepository: MenuRepository) {

    operator fun invoke (): Observable<List<String>> =
        menuRepository.menus ()
}

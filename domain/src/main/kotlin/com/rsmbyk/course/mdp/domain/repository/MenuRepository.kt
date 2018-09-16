package com.rsmbyk.course.mdp.domain.repository

import io.reactivex.Observable

interface MenuRepository {

    fun menus (): Observable<List<String>>
}

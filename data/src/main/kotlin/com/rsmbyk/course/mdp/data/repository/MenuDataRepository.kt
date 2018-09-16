package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.domain.repository.MenuRepository
import io.reactivex.Observable

class MenuDataRepository (private val context: Context): MenuRepository {

    override fun menus (): Observable<List<String>> {
        return Observable.create {
            it.onNext (context.resources.getStringArray (R.array.menus).toList ())
            it.onComplete ()
        }
    }
}

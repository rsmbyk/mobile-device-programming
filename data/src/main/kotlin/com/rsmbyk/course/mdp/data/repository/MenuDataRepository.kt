package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.domain.repository.MenuRepository

class MenuDataRepository (private val context: Context): MenuRepository {

    override fun menus (): List<String> =
        context.resources.getStringArray (R.array.menus).toList ()
}

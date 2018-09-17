package com.rsmbyk.course.mdp.model

import android.support.annotation.IdRes
import com.rsmbyk.course.mdp.R

enum class MenuModel (@IdRes val id: Int) {
    CALCULATOR (R.id.menu_calculator),
    CAMERA (R.id.menu_camera),
    NETWORKING (R.id.menu_networking),
    DATABASE (R.id.menu_database),
}

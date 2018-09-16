package com.rsmbyk.course.mdp.model

import android.support.annotation.IdRes
import com.rsmbyk.course.mdp.R

enum class OperatorModel (@IdRes val id: Int) {
    ADD (R.id.btn_add),
    SUBTRACT (R.id.btn_subtract),
    MULTIPLY (R.id.btn_multiply),
    DIVIDE (R.id.btn_divide)
}

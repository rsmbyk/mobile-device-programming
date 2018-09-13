package com.rsmbyk.course.mdp.common

import android.view.View

fun View.setVisible (condition: Boolean = true) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.show () {
    visibility = View.VISIBLE
}

fun View.hide () {
    visibility = View.GONE
}

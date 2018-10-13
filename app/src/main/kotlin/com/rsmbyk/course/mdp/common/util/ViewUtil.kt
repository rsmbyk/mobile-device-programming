package com.rsmbyk.course.mdp.common.util

import android.view.View

fun View.setVisible (condition: Boolean = true) {
    if (condition) show () else hide ()
}

fun View.show () {
    visibility = View.VISIBLE
}

fun View.hide () {
    visibility = View.GONE
}

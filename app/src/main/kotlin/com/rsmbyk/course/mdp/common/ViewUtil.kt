package com.rsmbyk.course.mdp.common

import android.view.View
import android.widget.TextView

fun View.setVisible (condition: Boolean = true) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.show () {
    visibility = View.VISIBLE
}

fun View.hide () {
    visibility = View.GONE
}

fun TextView.setIntText (value: Int?) {
    text = value?.toString ()
}

fun TextView.setErrorText (throwable: Throwable?) {
    text = throwable?.message
}

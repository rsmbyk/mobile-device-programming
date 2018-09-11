package com.rsmbyk.course.mdp.domain.common

interface VolleyRequestCallback<T> {
    fun onError (throwable: Throwable)
    fun onFinished (response: T)
}

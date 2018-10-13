package com.rsmbyk.course.mdp.data.api.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyRequestQueue (context: Context) {

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue (context)
    }

    fun <T> add (request: Request<T>): Request<T> =
        requestQueue.add (request)
}

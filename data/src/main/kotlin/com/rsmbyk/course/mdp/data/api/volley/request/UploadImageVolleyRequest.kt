package com.rsmbyk.course.mdp.data.api.volley.request

import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

class UploadImageVolleyRequest (
    url: String,
    private val params: MutableMap<String, String>,
    listener: (response: String) -> Unit,
    errorListener: (error: VolleyError) -> Unit)
        : StringRequest (Method.POST, url, listener, errorListener) {

    override fun getBodyContentType (): String =
        "application/x-www-form-urlencoded"

    override fun getParams (): MutableMap<String, String> =
        params
}

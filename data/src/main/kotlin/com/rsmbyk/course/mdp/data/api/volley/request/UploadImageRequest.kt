package com.rsmbyk.course.mdp.data.api.volley.request

import com.android.volley.toolbox.StringRequest
import com.rsmbyk.course.mdp.data.common.UploadImageListener

class UploadImageRequest (
    url: String,
    private val params: MutableMap<String, String>,
    listener: UploadImageListener)
        : StringRequest (Method.POST, url, listener::onResponse, listener::onErrorResponse) {

    override fun getBodyContentType (): String =
        "application/x-www-form-urlencoded"

    override fun getParams (): MutableMap<String, String> =
        params
}

package com.rsmbyk.course.mdp.data.common

import android.content.Context
import com.android.volley.Response
import com.android.volley.VolleyError
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import org.json.JSONObject

class UploadImageListener (
    private val context: Context,
    private val callback: UploadImageCallback)
        : Response.Listener<String>, Response.ErrorListener {

    override fun onResponse (response: String?) {
        val jsonResponse = JSONObject (response)
        val uploadImageResponse = UploadImageResponse (
            jsonResponse.getString (
                context.getString (R.string.upload_image_response_params_hasil)))
        callback.onSuccess (uploadImageResponse)
    }

    override fun onErrorResponse (error: VolleyError?) {
        error?.let (callback::onError)
    }
}

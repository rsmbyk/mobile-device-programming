package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.common.UploadImageListener
import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository

class UploadImageDataRepository (
    private val context: Context,
    private val volleyRequestQueue: VolleyRequestQueue)
        : UploadImageRepository {

    override fun uploadImage (uploadImageRequest: UploadImageRequest, callback: UploadImageCallback) {
        val url = context.getString (R.string.upload_image_url)
        val params = mutableMapOf (
            context.getString (R.string.upload_image_request_param_nrp)
                to uploadImageRequest.nrp,
            context.getString (R.string.upload_image_request_param_image)
                to uploadImageRequest.image.compress ().toBase64ImageString ())
        val listener = UploadImageListener (context, callback)
        val request = com.rsmbyk.course.mdp.data.api.volley.request.UploadImageRequest(url, params, listener)
        volleyRequestQueue.add (request)
    }
}

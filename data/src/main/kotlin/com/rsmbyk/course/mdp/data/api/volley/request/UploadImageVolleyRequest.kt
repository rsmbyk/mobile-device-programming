package com.rsmbyk.course.mdp.data.api.volley.request

import android.content.Context
import com.android.volley.VolleyError
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.parambuilder.UploadImageRequestParameterBuilder
import com.rsmbyk.course.mdp.data.api.volley.parser.UploadImageResponseParser
import com.rsmbyk.course.mdp.data.model.UploadImageRequestData
import com.rsmbyk.course.mdp.data.model.UploadImageResponseData

class UploadImageVolleyRequest (
    context: Context,
    request: UploadImageRequestData,
    listener: (response: UploadImageResponseData) -> Unit,
    errorListener: (error: VolleyError) -> Unit)
        : VolleyRequest<UploadImageRequestData, UploadImageResponseData> (
            Method.POST,
            context.getString (R.string.upload_image_url),
            request,
            listener,
            errorListener,
            UploadImageRequestParameterBuilder (context),
            UploadImageResponseParser ())

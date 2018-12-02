package com.rsmbyk.course.mdp.data.api.volley.request

import android.content.Context
import com.android.volley.VolleyError
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.parambuilder.UploadImageRequestParameterBuilder
import com.rsmbyk.course.mdp.data.model.SuperResponseData
import com.rsmbyk.course.mdp.data.model.UploadImageRequestData

class UploadImageVolleyRequest (
    context: Context,
    request: UploadImageRequestData,
    listener: (response: SuperResponseData) -> Unit,
    errorListener: (error: VolleyError) -> Unit)
        : SuperVolleyRequest<UploadImageRequestData> (
            context.getString (R.string.upload_image_url),
            request,
            listener,
            errorListener,
            UploadImageRequestParameterBuilder (context))

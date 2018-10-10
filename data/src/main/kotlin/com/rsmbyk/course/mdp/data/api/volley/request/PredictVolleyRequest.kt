package com.rsmbyk.course.mdp.data.api.volley.request

import android.content.Context
import com.android.volley.VolleyError
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.parambuilder.PredictRequestParameterBuilder
import com.rsmbyk.course.mdp.data.api.volley.parser.PredictResponseParser
import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.data.model.PredictResponseData

class PredictVolleyRequest (
    context: Context,
    request: PredictRequestData,
    listener: (response: PredictResponseData) -> Unit,
    errorListener: (error: VolleyError) -> Unit)
        : VolleyRequest<PredictRequestData, PredictResponseData> (
            Method.POST,
            context.getString (R.string.predict_image_url),
            request,
            listener,
            errorListener,
            PredictRequestParameterBuilder (context),
            PredictResponseParser (context))

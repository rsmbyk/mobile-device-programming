package com.rsmbyk.course.mdp.data.api.volley.request

import android.content.Context
import com.android.volley.VolleyError
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.parambuilder.PredictRequestParameterBuilder
import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.data.model.SuperResponseData

class PredictVolleyRequest (
    context: Context,
    request: PredictRequestData,
    listener: (response: SuperResponseData) -> Unit,
    errorListener: (error: VolleyError) -> Unit)
        : SuperVolleyRequest<PredictRequestData> (
            context.getString (R.string.signin_url),
            request,
            listener,
            errorListener,
            PredictRequestParameterBuilder (context))

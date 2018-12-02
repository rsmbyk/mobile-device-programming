package com.rsmbyk.course.mdp.data.api.volley.request

import com.android.volley.VolleyError
import com.rsmbyk.course.mdp.data.api.volley.parambuilder.ParameterBuilder
import com.rsmbyk.course.mdp.data.api.volley.parser.SuperResponseParser
import com.rsmbyk.course.mdp.data.model.SuperResponseData

open class SuperVolleyRequest<Request> (
    url: String,
    request: Request,
    listener: (response: SuperResponseData) -> Unit,
    errorListener: (error: VolleyError) -> Unit,
    parameterBuilder: ParameterBuilder<Request>)
        : VolleyRequest<Request, SuperResponseData> (
            Method.POST,
            url,
            request,
            listener,
            errorListener,
            parameterBuilder,
            SuperResponseParser ())

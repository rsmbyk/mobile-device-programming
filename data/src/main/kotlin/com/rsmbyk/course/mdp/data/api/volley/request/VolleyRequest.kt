package com.rsmbyk.course.mdp.data.api.volley.request

import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.rsmbyk.course.mdp.data.api.volley.parambuilder.ParameterBuilder
import com.rsmbyk.course.mdp.data.api.volley.parser.Parser

open class VolleyRequest<Request, Response> (
    method: Int,
    url: String,
    private val request: Request,
    listener: (response: Response) -> Unit,
    errorListener: (error: VolleyError) -> Unit,
    private val parameterBuilder: ParameterBuilder<Request>,
    parser: Parser<Response>)
        : StringRequest (method, url, { listener (parser.parse (it)) }, errorListener) {

    override fun getParams (): MutableMap<String, String> =
        parameterBuilder.build (request)
}

package com.rsmbyk.course.mdp.data.api.volley.parser

import com.google.gson.Gson
import com.rsmbyk.course.mdp.data.model.SuperResponseData

class SuperResponseParser: Parser<SuperResponseData> {

    override fun parse (response: String): SuperResponseData =
        Gson ().fromJson (response, SuperResponseData::class.java)
}

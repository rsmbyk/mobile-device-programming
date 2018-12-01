package com.rsmbyk.course.mdp.data.api.volley.parser

import android.content.Context
import com.google.gson.Gson
import com.rsmbyk.course.mdp.data.model.PredictResponseData

class PredictResponseParser (private val context: Context): Parser<PredictResponseData> {

    override fun parse (response: String): PredictResponseData {
        return Gson ().fromJson (response, PredictResponseData::class.java)
    }
}

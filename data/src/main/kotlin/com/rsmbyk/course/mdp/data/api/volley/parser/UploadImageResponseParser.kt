package com.rsmbyk.course.mdp.data.api.volley.parser

import android.content.Context
import com.google.gson.Gson
import com.rsmbyk.course.mdp.data.model.UploadImageResponseData

class UploadImageResponseParser (context: Context): Parser<UploadImageResponseData> {

    override fun parse (response: String): UploadImageResponseData {
        return Gson ().fromJson (response, UploadImageResponseData::class.java)
    }
}

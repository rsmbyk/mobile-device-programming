package com.rsmbyk.course.mdp.data.api.volley.parser

import com.rsmbyk.course.mdp.data.model.UploadImageResponseData

class UploadImageResponseParser: Parser<UploadImageResponseData> {
    override fun parse (response: String): UploadImageResponseData {
        return UploadImageResponseData ()
    }
}

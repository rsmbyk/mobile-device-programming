package com.rsmbyk.course.mdp.data.api.volley.parambuilder

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.common.compress
import com.rsmbyk.course.mdp.data.common.toBase64ImageStringWithoutPrefix
import com.rsmbyk.course.mdp.data.model.PredictRequestData

class PredictRequestParameterBuilder (private val context: Context)
    : ParameterBuilder<PredictRequestData> {

    override fun build (request: PredictRequestData): MutableMap<String, String> {
        return request.run {
            mutableMapOf (
                context.getString (R.string.predict_image_request_param_nrp)
                    to request.nrp,
                context.getString (R.string.predict_image_request_param_image)
                    to request.image.compress ().toBase64ImageStringWithoutPrefix ()
            )
        }
    }
}

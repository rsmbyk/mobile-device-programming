package com.rsmbyk.course.mdp.data.api.volley.parambuilder

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.common.compress
import com.rsmbyk.course.mdp.data.common.toBase64ImageString
import com.rsmbyk.course.mdp.data.model.UploadImageRequestData

class UploadImageRequestParameterBuilder (private val context: Context): ParameterBuilder<UploadImageRequestData> {

    override fun build (request: UploadImageRequestData): MutableMap<String, String> {
        return request.run {
            mutableMapOf (
                context.getString (R.string.upload_image_request_param_nrp)
                    to context.getString (R.string.upload_image_request_nrp),
                context.getString (R.string.upload_image_request_param_image)
                    to image.compress ().toBase64ImageString ()
            )
        }
    }
}

package com.rsmbyk.course.mdp.data.api.volley.parambuilder

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.model.PredictRequestData

class PredictRequestParameterBuilder (private val context: Context)
    : ParameterBuilder<PredictRequestData> {

    override fun build (request: PredictRequestData): MutableMap<String, String> {
        return request.run {
            mutableMapOf (
                context.getString (R.string.signin_request_param_idUser)
                    to request.idUser,
                context.getString (R.string.signin_request_param_password)
                    to request.password,
                context.getString (R.string.signin_request_param_image)
                    to request.image,
                context.getString (R.string.signin_request_param_latitude)
                    to request.agenda.coordinate.latitude.toString (),
                context.getString (R.string.signin_request_param_longitude)
                    to request.agenda.coordinate.longitude.toString (),
                context.getString (R.string.signin_request_param_idAgenda)
                    to request.agenda.id
            )
        }
    }
}

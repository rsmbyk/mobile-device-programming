package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.api.volley.request.UploadImageVolleyRequest
import com.rsmbyk.course.mdp.data.common.compress
import com.rsmbyk.course.mdp.data.common.toBase64ImageString
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Observable
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class UploadImageDataRepository (
    private val context: Context,
    private val volleyRequestQueue: VolleyRequestQueue)
        : UploadImageRepository {

    override fun uploadImage (uploadImageRequest: UploadImageRequest): Observable<UploadImageResponse> {
        val url = context.getString (R.string.upload_image_url)
        val params = mutableMapOf (
            context.getString (R.string.upload_image_request_param_nrp)
                to uploadImageRequest.nrp,
            context.getString (R.string.upload_image_request_param_image)
                to uploadImageRequest.image.compress ().toBase64ImageString ())
        return Observable.create { emitter ->
            val startTime = System.nanoTime()
            val request = UploadImageVolleyRequest (url, params, { response ->
                val jsonResponse = JSONObject (response)
                val elapsedTimeInNano = System.nanoTime () - startTime
                val uploadImageResponse = UploadImageResponse (
                    jsonResponse.getString (
                        context.getString (R.string.upload_image_response_params_hasil)),
                    TimeUnit.NANOSECONDS.toMillis (elapsedTimeInNano) / 1000f)
                emitter.onNext (uploadImageResponse)
                emitter.onComplete ()
            }, { error ->
                emitter.onError (error)
                emitter.onComplete ()
            })
            volleyRequestQueue.add (request)
        }
    }
}

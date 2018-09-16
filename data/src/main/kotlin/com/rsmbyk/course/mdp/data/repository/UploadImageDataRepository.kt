package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.api.volley.request.UploadImageVolleyRequest
import com.rsmbyk.course.mdp.data.common.compress
import com.rsmbyk.course.mdp.data.common.toBase64ImageString
import com.rsmbyk.course.mdp.data.db.UploadImageDao
import com.rsmbyk.course.mdp.data.model.UploadImageData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

class UploadImageDataRepository (
    private val context: Context,
    private val privateImageDirectory: File,
    private val mapper: Mapper<UploadImage, UploadImageData>,
    private val uploadImageDao: UploadImageDao,
    private val volleyRequestQueue: VolleyRequestQueue)
        : UploadImageRepository {

    companion object {
        private const val RESPONSE_HASIL_SUKSES = "sukses"
    }

    override fun uploadImage (uploadImageRequest: UploadImageRequest): Observable<UploadImage> {
        val url = context.getString (R.string.upload_image_url)
        val params = mutableMapOf (
            context.getString (R.string.upload_image_request_param_nrp)
                to uploadImageRequest.nrp,
            context.getString (R.string.upload_image_request_param_image)
                to uploadImageRequest.image.compress ().toBase64ImageString ())
        return Observable.create { emitter ->
            val startTime = System.nanoTime ()
            val request = UploadImageVolleyRequest (url, params, { response ->
                val hasil =
                    JSONObject (response).getString (
                        context.getString (R.string.upload_image_response_params_hasil))

                assert (hasil == RESPONSE_HASIL_SUKSES)

                val elapsedTime =
                    TimeUnit.NANOSECONDS.toMillis (System.nanoTime () - startTime) / 1000f

                val target: File = uploadImageRequest.image.run {
                    val target = File (privateImageDirectory, "${uploadImageRequest.requestCode}.jpg")
                    copyTo (target, true)
                }

                val uploadImageData = UploadImageData (
                    id = uploadImageRequest.requestCode,
                    path = target.absolutePath,
                    elapsedTime = elapsedTime,
                    uploadTime = System.currentTimeMillis ())

                Single.fromCallable { uploadImageDao.insert (uploadImageData) }
                    .subscribeOn (Schedulers.io ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe { _ ->
                        emitter.onNext (mapper.mapToEntity (uploadImageData))
                        emitter.onComplete ()
                    }
            }, { error ->
                emitter.onError (error)
                emitter.onComplete ()
            })
            volleyRequestQueue.add (request)
        }
    }
}

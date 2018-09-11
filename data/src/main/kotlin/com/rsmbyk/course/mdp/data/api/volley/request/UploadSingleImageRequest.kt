package com.rsmbyk.course.mdp.data.api.volley.request

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.android.volley.toolbox.StringRequest
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.domain.common.VolleyRequestCallback
import java.io.ByteArrayOutputStream
import java.io.File

class UploadSingleImageRequest (
    private val context: Context,
    url: String,
    private val nrp: String,
    private val imageFile: File,
    callback: VolleyRequestCallback<String>)
        : StringRequest (Method.POST, url, callback::onFinished, callback::onError) {

    override fun getBodyContentType (): String =
        "application/x-www-form-urlencoded"

    override fun getParams (): MutableMap<String, String> {
        val bos: ByteArrayOutputStream = imageFile.compressImageFile ()
        val b64EncodedImage: String = Base64.encodeToString (bos.toByteArray(), Base64.DEFAULT)

        return mutableMapOf (
            context.getString (R.string.request_param_nrp) to nrp,
            context.getString (R.string.request_param_image) to b64EncodedImage)
    }

    private fun File.compressImageFile (): ByteArrayOutputStream {
        val image: Bitmap = BitmapFactory.decodeFile (path)
        val scaledImage: Bitmap = Bitmap.createScaledBitmap (image, 512, 512, true)
        val bos = ByteArrayOutputStream ()
        scaledImage.compress (Bitmap.CompressFormat.JPEG, 100, bos)
        return bos
    }
}

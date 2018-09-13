package com.rsmbyk.course.mdp.domain.common

import com.rsmbyk.course.mdp.domain.model.UploadImageResponse

interface UploadImageCallback {
    fun onSuccess (requestCode: Int, response: UploadImageResponse)
    fun onError (requestCode: Int, throwable: Throwable)
    fun onComplete (requestCode: Int)
}

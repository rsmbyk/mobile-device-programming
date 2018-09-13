package com.rsmbyk.course.mdp.domain.common

import com.rsmbyk.course.mdp.domain.model.UploadImageResponse

interface UploadImageCallback {
    fun onSuccess (response: UploadImageResponse)
    fun onError (throwable: Throwable)
}

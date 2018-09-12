package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.common.VolleyRequestCallback
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import java.io.File

class NetworkingViewModel (
    val uploadImageDirectory: File,
    private val getCapturedImageUseCase: GetCapturedImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase)
        : ViewModel () {

    val state = MutableLiveData<NetworkingViewState> ()
    val uploadList = mutableListOf<UploadImageModel> ()
    val uploadListAdapter = UploadImageAdapter (uploadList)

    init {
        state.value = NetworkingViewState ()
    }

    fun uploadImages (url: String, nrp: String, callback: UploadImageCallback) {
        state.postValue (state.value?.copy (isUploading = true))

        uploadList.forEachIndexed { index, item ->
            state.postValue (state.value?.copy (
                uploadIndex = 0,
                uploadName = uploadList[0].file.nameWithoutExtension
            ))

            uploadImageUseCase (url, nrp, item.file, object : VolleyRequestCallback<String> {
                override fun onError (throwable: Throwable) {
                    callback.onError (throwable)
                    throw throwable
                }

                override fun onFinished (response: String) {
                    if (index == uploadList.size - 1)
                        state.postValue (NetworkingViewState ())
                    else
                        state.postValue (state.value!!.copy (
                            uploadIndex = index,
                            uploadName = uploadList[index].file.nameWithoutExtension
                        ))
                }
            })
        }
    }

    fun addCapturedImage () {
        val capturedImage = getCapturedImageUseCase (uploadImageDirectory)
        uploadList.add (UploadImageModel (capturedImage))
        uploadListAdapter.notifyItemInserted (0)
        state.postValue (state.value?.copy (uploadTotal = uploadList.size))
    }

    fun clearUploadList () {
        uploadList.forEach { it.file.delete () }
        uploadList.clear ()
        state.postValue (state.value?.copy (uploadTotal = 0))
    }

    interface UploadImageCallback {
        fun onError (throwable: Throwable)
        fun onFinished ()
    }
}

package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadListImageModel
import com.rsmbyk.course.mdp.model.UploadListImageModel.UploadProgress
import com.rsmbyk.course.mdp.ui.networking.NetworkingViewState.UploadState
import java.io.File

class NetworkingViewModel (
    val uploadImageDirectory: File,
    private val getCapturedImageUseCase: GetCapturedImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase)
        : ViewModel () {

    companion object {
        private const val RESPONSE_HASIL_SUKSES = "sukses"
    }

    val state = MutableLiveData<NetworkingViewState> ()
    val uploadList = mutableListOf<UploadListImageModel> ()
    val uploadListAdapter = UploadImageListAdapter (uploadList)

    init {
        state.value = NetworkingViewState ()
    }

    fun uploadImages (nrp: String) {
        uploadList
            .filter { it.uploadProgress == UploadProgress.FAILED }
            .forEach { it.uploadProgress = UploadProgress.IDLE }

        val firstIndex = getNextImageIndex ()
        val request = UploadImageRequest (nrp, uploadList[firstIndex].file)
        state.value = state.value!!.copy (
            uploadState = UploadState.UPLOADING,
            uploadSuccess = 0,
            uploadProgressIndex = firstIndex,
            uploadProgressName = uploadList[firstIndex].file.nameWithoutExtension)
        uploadListAdapter.setItemProgress (firstIndex, UploadProgress.UPLOADING)

        uploadImageUseCase (firstIndex, request, object : UploadImageCallback {
            override fun onSuccess (requestCode: Int, response: UploadImageResponse) {
                assert (response.hasil == RESPONSE_HASIL_SUKSES)
                state.value = state.value!!.copy (
                    uploadSuccess = state.value!!.uploadSuccess + 1)
                uploadListAdapter.setItemProgress (requestCode, UploadProgress.SUCCESS)
                uploadListAdapter.setItemElapsedTime (requestCode, response.elapsedTimeInSecond)
            }

            override fun onError (requestCode: Int, throwable: Throwable) {
                uploadListAdapter.setItemProgress (requestCode, UploadProgress.FAILED)
            }

            override fun onComplete (requestCode: Int) {
                val nextIndex = getNextImageIndex ()
                if (nextIndex == -1)
                    state.value = state.value!!.copy (
                        uploadState = UploadState.FINISHED)
                else {
                    state.value = state.value!!.copy (
                        uploadProgressIndex = nextIndex,
                        uploadProgressName = uploadList[nextIndex].file.nameWithoutExtension)
                    uploadListAdapter.setItemProgress (nextIndex, UploadProgress.UPLOADING)
                    uploadImageUseCase (nextIndex, UploadImageRequest (nrp, uploadList[nextIndex].file), this)
                }
            }
        })
    }

    private fun UploadImageListAdapter.setItemProgress (position: Int, uploadProgress: UploadProgress) {
        uploadList[position].uploadProgress = uploadProgress
        notifyItemChanged (position)
    }

    private fun UploadImageListAdapter.setItemElapsedTime (position: Int, elapsedTime: Float) {
        uploadList[position].elapsedTime = elapsedTime
        notifyItemChanged (position)
    }

    private fun getNextImageIndex () =
        uploadList.indexOf (uploadList.firstOrNull { it.uploadProgress == UploadProgress.IDLE })

    fun addCapturedImage () {
        val capturedImage = getCapturedImageUseCase (uploadImageDirectory)
        uploadList.add (UploadListImageModel (capturedImage))
        uploadListAdapter.notifyItemInserted (uploadList.size - 1)
        state.value = state.value!!.copy (
            uploadTotal = uploadList.filter { it.uploadProgress == UploadProgress.IDLE }.size)
    }

    fun clearUploadList () {
        uploadList.forEach { it.file.delete () }
        uploadList.clear ()
        uploadListAdapter.notifyDataSetChanged ()
        state.value = NetworkingViewState ()
    }
}

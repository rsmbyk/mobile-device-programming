package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.common.UploadImageCallback
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadListImageModel
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
        val request = UploadImageRequest (nrp, uploadList[0].file)

        val firstIndex = uploadList.indexOfFirst (UploadListImageModel::isUploaded)
        state.value = state.value!!.copy (
            uploadState = UploadState.UPLOADING,
            uploadProgressIndex = firstIndex,
            uploadProgressName = uploadList[firstIndex].file.nameWithoutExtension)

        uploadImageUseCase (request, object : UploadImageCallback {
            override fun onSuccess (response: UploadImageResponse) {
                assert (response.hasil == RESPONSE_HASIL_SUKSES)
                state.value = state.value!!.copy (
                    uploadSuccess = state.value!!.uploadSuccess + 1)
                val index = state.value!!.uploadProgressIndex
                uploadList[index].isUploaded = true
                uploadListAdapter.notifyItemChanged (index)
                onComplete ()
            }

            override fun onError (throwable: Throwable) =
                onComplete ()

            private fun onComplete () {
                val index = getNextImageIndex ()
                if (index == -1)
                    state.value = state.value!!.copy (
                        uploadState = UploadState.FINISHED)
                else {
                    state.value = state.value!!.copy (
                        uploadProgressIndex = index + 1,
                        uploadProgressName = uploadList[index].file.nameWithoutExtension)
                    uploadImageUseCase (UploadImageRequest (nrp, uploadList[index].file), this)
                }
            }
        })
    }

    private fun getNextImageIndex () =
        uploadList.indexOf (
            uploadList
                .filterNot (UploadListImageModel::isUploaded)
                .firstOrNull ())

    fun addCapturedImage () {
        val capturedImage = getCapturedImageUseCase (uploadImageDirectory)
        uploadList.add (UploadListImageModel (capturedImage))
        uploadListAdapter.notifyItemInserted (uploadList.size - 1)
        state.value = state.value!!.copy (
            uploadTotal = uploadList.filter (UploadListImageModel::isUploaded).size)
    }

    fun clearUploadList () {
        uploadList.forEach { it.file.delete () }
        uploadList.clear ()
        uploadListAdapter.notifyDataSetChanged ()
        state.value = NetworkingViewState ()
    }
}

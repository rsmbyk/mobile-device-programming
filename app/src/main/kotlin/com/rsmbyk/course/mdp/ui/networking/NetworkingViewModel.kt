package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadListImageModel
import com.rsmbyk.course.mdp.model.UploadListImageModel.UploadProgress
import com.rsmbyk.course.mdp.ui.networking.NetworkingViewState.UploadState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class NetworkingViewModel (
    private val nrp: String,
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
    val newImage = MutableLiveData<File> ()

    init {
        state.value = NetworkingViewState ()
    }

    fun uploadImages () {
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

        uploadImageUseCase (request)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (::onUploadItemResponse, ::onUploadItemError, ::onUploadItemComplete)
    }

    private fun onUploadItemResponse (response: UploadImageResponse) {
        assert (response.hasil == RESPONSE_HASIL_SUKSES)
        val index = state.value!!.uploadProgressIndex
        uploadListAdapter.setItemProgress (index, UploadProgress.SUCCESS)
        uploadListAdapter.setItemElapsedTime (index, response.elapsedTimeInSecond)
        state.value = state.value!!.copy (
            uploadSuccess = state.value!!.uploadSuccess + 1)
    }

    private fun onUploadItemError (throwable: Throwable) {
        val index = state.value!!.uploadProgressIndex
        uploadListAdapter.setItemProgress (index, UploadProgress.FAILED)
    }

    private fun UploadImageListAdapter.setItemProgress (position: Int, uploadProgress: UploadProgress) {
        uploadList[position].uploadProgress = uploadProgress
        notifyItemChanged (position)
    }

    private fun onUploadItemComplete () {
        val nextIndex = getNextImageIndex ()
        if (nextIndex == -1)
            state.value = state.value!!.copy (
                uploadState = UploadState.FINISHED)
        else {
            state.value = state.value!!.copy (
                uploadProgressIndex = nextIndex,
                uploadProgressName = uploadList[nextIndex].file.nameWithoutExtension)
            uploadListAdapter.setItemProgress (nextIndex, UploadProgress.UPLOADING)
            uploadImageUseCase (UploadImageRequest (nrp, uploadList[nextIndex].file))
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (::onUploadItemResponse, ::onUploadItemError, ::onUploadItemComplete)
        }
    }

    private fun UploadImageListAdapter.setItemElapsedTime (position: Int, elapsedTime: Float) {
        uploadList[position].elapsedTime = elapsedTime
        notifyItemChanged (position)
    }

    private fun getNextImageIndex () =
        uploadList.indexOf (uploadList.firstOrNull { it.uploadProgress == UploadProgress.IDLE })

    fun addCapturedImage () {
        getCapturedImageUseCase (uploadImageDirectory)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe { capturedImage ->
                uploadList.add (UploadListImageModel (capturedImage))
                uploadListAdapter.notifyItemInserted (uploadList.size - 1)
                state.value = state.value!!.copy (
                    uploadTotal = uploadList.filter { it.uploadProgress == UploadProgress.IDLE }.size)
                newImage.value = capturedImage
            }
    }

    fun clearUploadList () {
        uploadList.forEach { it.file.delete () }
        uploadList.clear ()
        uploadListAdapter.notifyDataSetChanged ()
        state.value = NetworkingViewState ()
    }
}

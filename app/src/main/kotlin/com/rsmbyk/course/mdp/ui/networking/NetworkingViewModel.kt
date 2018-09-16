package com.rsmbyk.course.mdp.ui.networking

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.model.UploadImageModel.UploadProgress
import com.rsmbyk.course.mdp.ui.networking.NetworkingViewState.UploadState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class NetworkingViewModel (
    private val nrp: String,
    val uploadImageDirectory: File,
    private val mapper: Mapper<UploadImage, UploadImageModel>,
    private val getCapturedImageUseCase: GetCapturedImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase)
        : ViewModel () {

    val state = MutableLiveData<NetworkingViewState> ()
    val uploadList = mutableListOf<UploadImageModel> ()
    val uploadListAdapter = UploadImageListAdapter (uploadList)
    val newImage = MutableLiveData<File> ()

    init {
        state.value = NetworkingViewState ()
    }

    fun uploadImages () {
        uploadList
            .filter { it.uploadProgress == UploadProgress.FAILED }
            .forEach { it.uploadProgress = UploadProgress.IDLE }

        val next = uploadList.first { it.uploadProgress == UploadProgress.IDLE }
        state.value = state.value!!.copy (
            uploadState = UploadState.UPLOADING,
            uploadSuccess = 0,
            uploadProgressIndex = next.index,
            uploadProgressName = next.file.nameWithoutExtension)
        next.uploadProgress = UploadProgress.UPLOADING
        uploadListAdapter.notifyItemChanged (next.index)

        uploadImageUseCase (UploadImageRequest (next.index, nrp, next.file))
            .map (mapper::mapToModel)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (::onUploadItemResponse, ::onUploadItemError, ::onUploadItemComplete)
    }

    private fun onUploadItemResponse (response: UploadImageModel) {
        response.uploadProgress = UploadProgress.SUCCESS
        uploadList[response.index] = response
        uploadListAdapter.notifyItemChanged (response.index)
        state.value = state.value!!.copy (
            uploadSuccess = state.value!!.uploadSuccess + 1)
    }

    private fun onUploadItemError (throwable: Throwable) {
        val item = uploadList.first { it.uploadProgress == UploadProgress.UPLOADING }
        item.uploadProgress = UploadProgress.FAILED
        uploadListAdapter.notifyItemChanged (item.index)
    }

    private fun onUploadItemComplete () {
        try {
            val next = uploadList.first { it.uploadProgress == UploadProgress.IDLE }
            state.value = state.value!!.copy (
                uploadProgressIndex = next.index,
                uploadProgressName = next.file.nameWithoutExtension)
            next.uploadProgress = UploadProgress.UPLOADING
            uploadListAdapter.notifyItemChanged (next.index)
            uploadImageUseCase (UploadImageRequest (next.index, nrp, next.file))
                .map (mapper::mapToModel)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (::onUploadItemResponse, ::onUploadItemError, ::onUploadItemComplete)
        } catch (e: NoSuchElementException) {
            state.value = state.value!!.copy (
                uploadState = UploadState.FINISHED)
        }
    }

    fun addCapturedImage () {
        getCapturedImageUseCase (uploadImageDirectory)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (::addImage)
    }

    private fun addImage (image: File) {
        uploadList.add (UploadImageModel (uploadList.lastIndex + 1, image))
        uploadListAdapter.notifyItemInserted (uploadList.lastIndex)
        state.value = state.value!!.copy (
            uploadTotal = uploadList.filter { it.uploadProgress == UploadProgress.IDLE }.size)
        newImage.value = image
    }

    fun clearUploadList () {
        uploadList.forEach { it.file.delete () }
        uploadList.clear ()
        uploadListAdapter.notifyDataSetChanged ()
        state.value = NetworkingViewState ()
    }
}

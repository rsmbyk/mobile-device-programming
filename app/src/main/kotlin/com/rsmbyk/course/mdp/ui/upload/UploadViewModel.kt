package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetUploadListUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveUploadImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.UploadImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.model.UploadImageResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class UploadViewModel (
    private val getUploadListUseCase: GetUploadListUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val saveUploadImageUseCase: SaveUploadImageUseCase,
    private val uploadImageModelMapper: Mapper<UploadImage, UploadImageModel>,
    private val uploadImageResponseModelMapper: Mapper<UploadImageResponse, UploadImageResponseModel>)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val uploadList = MutableLiveData<List<UploadImageModel>> ()
    private lateinit var backingUploadList: MutableList<UploadImageModel>
    val changedUploadImageIndex = MutableLiveData<Int> ()
    val error = MutableLiveData<Throwable> ()

    val canUpdateItem
        get() = backingUploadList.all { it.state == UploadImageModel.State.IDLE }

    private var startTime: Long = 0

    fun initializeUploadList (): List<UploadImageModel> {
        if (!::backingUploadList.isInitialized) {
            backingUploadList = getUploadListUseCase ()
                .asSequence ()
                .mapIndexed { index, name -> UploadImageModel (index, name) }
                .toMutableList ()
        }
        uploadList.value = backingUploadList
        return backingUploadList
    }

    private fun notify (index: Int) {
        uploadList.value = backingUploadList
        changedUploadImageIndex.value = index
    }

    fun setUploadImage (index: Int, image: ByteArray) {
        backingUploadList[index] =
            backingUploadList[index].copy (image = image)
        notify (index)
    }

    fun removeUploadImage (index: Int) {
        backingUploadList[index] =
            UploadImageModel (index, backingUploadList[index].text)
        notify (index)
    }

    fun clearUploadList () {
        backingUploadList.forEachIndexed { index, _ ->
            removeUploadImage (index) }
    }

    fun startUpload () {
        backingUploadList.firstOrNull { it.state != UploadImageModel.State.SUCCESS }?.apply {
            state = UploadImageModel.State.UPLOADING
            startTime = System.nanoTime ()
            notify (index)
            uploadImage (index)
        }
    }

    private fun uploadImage (index: Int) {
        disposable.add (uploadImageUseCase (UploadImageRequest (backingUploadList[index].image!!))
            .map (uploadImageResponseModelMapper::mapToModel)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (::onUploadSuccess, ::onUploadFailure))
    }

    private fun onUploadSuccess (response: UploadImageResponseModel) {
        backingUploadList.first { it.state == UploadImageModel.State.UPLOADING }.apply {
            state = UploadImageModel.State.SUCCESS
            elapsedTime = TimeUnit.NANOSECONDS.toMillis (System.nanoTime () - startTime) / 1000f
            timestamp = System.currentTimeMillis ()
            saveUploadImage (index)
            notify (index)
            startUpload ()
        }
    }

    private fun onUploadFailure (t: Throwable) {
        backingUploadList.first { it.state == UploadImageModel.State.UPLOADING }.apply {
            state = UploadImageModel.State.FAILED
            notify (index)
            error.value = t
        }
    }

    private fun saveUploadImage (index: Int) {
        Executors.newSingleThreadExecutor ().submit {
            saveUploadImageUseCase (
                uploadImageModelMapper.mapToEntity (backingUploadList[index]))
        }
    }

    override fun onCleared () =
        disposable.clear ()
}

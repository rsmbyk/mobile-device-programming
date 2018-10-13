package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImageNamesUseCase
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
    private val getUploadImageNamesUseCase: GetUploadImageNamesUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val saveUploadImageUseCase: SaveUploadImageUseCase,
    private val uploadImageMapper: Mapper<UploadImage, UploadImageModel>,
    private val uploadImageResponseMapper: Mapper<UploadImageResponse, UploadImageResponseModel>)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val uploadImages = MutableLiveData<List<UploadImageModel>> ()
    private lateinit var backingUploadImages: MutableList<UploadImageModel>
    val changedUploadImageIndex = MutableLiveData<Int> ()
    val error = MutableLiveData<Throwable> ()

    private var startTime: Long = 0

    fun initializeUploadList (): List<UploadImageModel> {
        if (!::backingUploadImages.isInitialized) {
            backingUploadImages = getUploadImageNamesUseCase ()
                .asSequence ()
                .mapIndexed { index, name -> UploadImageModel (index, name) }
                .toMutableList ()
        }
        
        uploadImages.value = backingUploadImages
        return backingUploadImages
    }

    private fun notify (index: Int) {
        uploadImages.value = backingUploadImages
        changedUploadImageIndex.value = index
    }

    fun canUpdateItem () =
        backingUploadImages.all { it.state == UploadImageModel.State.IDLE }

    fun setUploadImage (index: Int, image: ByteArray) {
        backingUploadImages[index] =
            backingUploadImages[index].copy (image = image)
        notify (index)
    }

    fun removeUploadImage (index: Int) {
        backingUploadImages[index] =
            UploadImageModel (index, backingUploadImages[index].text)
        notify (index)
    }

    fun clearUploadList () {
        backingUploadImages.forEachIndexed { index, _ ->
            removeUploadImage (index) }
    }

    fun startUpload () {
        backingUploadImages.firstOrNull { it.state != UploadImageModel.State.SUCCESS }?.apply {
            state = UploadImageModel.State.UPLOADING
            startTime = System.nanoTime ()
            notify (index)
            uploadImage (index)
        }
    }

    private fun uploadImage (index: Int) {
        disposable.add (
            uploadImageUseCase (UploadImageRequest (backingUploadImages[index].image!!))
                .map (uploadImageResponseMapper::mapToModel)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (::onUploadSuccess, ::onUploadFailure))
    }

    private fun onUploadSuccess (response: UploadImageResponseModel) {
        backingUploadImages.first { it.state == UploadImageModel.State.UPLOADING }.apply {
            state = UploadImageModel.State.SUCCESS
            elapsedTime = TimeUnit.NANOSECONDS.toMillis (System.nanoTime () - startTime) / 1000f
            timestamp = System.currentTimeMillis ()
            saveUploadImage (index)
            notify (index)
            startUpload ()
        }
    }

    private fun onUploadFailure (t: Throwable) {
        backingUploadImages.first { it.state == UploadImageModel.State.UPLOADING }.apply {
            state = UploadImageModel.State.FAILED
            notify (index)
            error.value = t
        }
    }

    private fun saveUploadImage (index: Int) {
        Executors.newSingleThreadExecutor ().execute {
            saveUploadImageUseCase (
                uploadImageMapper.mapToEntity (backingUploadImages[index]))
        }
    }

    override fun onCleared () =
        disposable.clear ()
}

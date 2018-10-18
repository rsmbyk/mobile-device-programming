package com.rsmbyk.course.mdp.ui.upload

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImageNamesUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImagesUseCase
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
    private val getUploadImagesUseCase: GetUploadImagesUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val saveUploadImageUseCase: SaveUploadImageUseCase,
    private val uploadImageMapper: Mapper<UploadImage, UploadImageModel>,
    private val uploadImageResponseMapper: Mapper<UploadImageResponse, UploadImageResponseModel>)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    private lateinit var uploadImages: MutableList<UploadImageModel>
    private lateinit var uploadImageNames: List<String>
    val addedUploadImageIndex = MutableLiveData<Int> ()
    val changedUploadImageIndex = MutableLiveData<Int> ()
    val error = MutableLiveData<Throwable> ()

    private var startTime: Long = 0

    fun initializeUploadList (): List<UploadImageModel> {
        if (!::uploadImages.isInitialized) {
            uploadImageNames = getUploadImageNamesUseCase ()
            uploadImages = mutableListOf ()
            disposable.add (
                getUploadImagesUseCase ()
                    .map (uploadImageMapper::mapToModel)
                    .subscribeOn (Schedulers.io ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (::onUploadImagesLoaded))
        }

        return uploadImages
    }

    private fun onUploadImagesLoaded (uploadImages: List<UploadImageModel>?) {
        uploadImages?.forEachIndexed { index, uploadImageModel ->
            this.uploadImages.add (uploadImageModel)
            notifyAdded (index)
        }

        this.uploadImages.size.also {
            uploadImageNames.getOrNull (it)?.apply {
                this@UploadViewModel.uploadImages.add (
                    UploadImageModel (it, this))
                notifyAdded (it)
            }
        }
    }

    private fun notifyAdded (index: Int) =
        addedUploadImageIndex.setValue (index)

    private fun notifyChanged (index: Int) =
        changedUploadImageIndex.setValue (index)

    fun setUploadImage (index: Int, image: ByteArray) {
        uploadImages[index] =
            uploadImages[index].copy (image = image)
        notifyChanged (index)
    }

    fun startUpload () {
        uploadImages.firstOrNull { it.state == UploadImageModel.State.IDLE || it.state == UploadImageModel.State.FAILED }?.apply {
            state = UploadImageModel.State.UPLOADING
            startTime = System.nanoTime ()
            notifyChanged (index)
            uploadImage (index)
        }
    }

    private fun uploadImage (index: Int) {
        disposable.add (
            uploadImageUseCase (UploadImageRequest (uploadImages[index].image!!))
                .map (uploadImageResponseMapper::mapToModel)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (::onUploadSuccess, ::onUploadFailure))
    }

    private fun onUploadSuccess (response: UploadImageResponseModel) {
        if (response.msg == UploadImageResponseModel.Message.DETECTED) {
            uploadImages.first { it.state == UploadImageModel.State.UPLOADING }.apply {
                state = UploadImageModel.State.SUCCESS
                elapsedTime = TimeUnit.NANOSECONDS.toMillis (System.nanoTime () - startTime) / 1000f
                timestamp = System.currentTimeMillis ()
                saveUploadImage (index)
                notifyChanged (index)
                uploadImages.size.also {
                    uploadImageNames.getOrNull (it)?.apply {
                        uploadImages.add (
                            UploadImageModel (it, this))
                        notifyAdded (it)
                    }
                }
            }
        } else {
            onUploadFailure (Throwable ("Face not detected"))
        }
    }

    private fun onUploadFailure (t: Throwable) {
        uploadImages.first { it.state == UploadImageModel.State.UPLOADING }.apply {
            state = UploadImageModel.State.FAILED
            notifyChanged (index)
            error.value = t
        }
    }

    private fun saveUploadImage (index: Int) {
        Executors.newSingleThreadExecutor ().execute {
            saveUploadImageUseCase (
                uploadImageMapper.mapToEntity (uploadImages[index]))
        }
    }

    override fun onCleared () =
        disposable.clear ()
}

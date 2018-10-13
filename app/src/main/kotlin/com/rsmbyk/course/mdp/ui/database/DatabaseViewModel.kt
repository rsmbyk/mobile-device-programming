package com.rsmbyk.course.mdp.ui.database

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.usecase.GetUploadImagesUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DatabaseViewModel (
    private val getUploadImagesUseCase: GetUploadImagesUseCase,
    private val uploadImageMapper: Mapper<UploadImage, UploadImageModel>)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val uploadImages = MutableLiveData<List<UploadImageModel>> ()

    init {
        getUploadImages ()
    }

    private fun getUploadImages () {
        disposable.add (
            getUploadImagesUseCase ()
                .map (uploadImageMapper::mapToModel)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (uploadImages::setValue))
    }

    override fun onCleared () =
        disposable.clear ()
}

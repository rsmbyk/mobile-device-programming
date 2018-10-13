package com.rsmbyk.course.mdp.ui.gallery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetGalleryImagesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveGalleryImageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GalleryViewModel (
        private val getGalleryImagesUseCase: GetGalleryImagesUseCase,
        private val saveGalleryImageUseCase: SaveGalleryImageUseCase)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val images = MutableLiveData<List<ByteArray>> ()

    fun getImages () {
        if (images.value != null)
            images.value = images.value
        else
            disposable.add (
                getGalleryImagesUseCase ()
                    .subscribeOn (Schedulers.io ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (images::setValue))
    }

    fun saveImage (image: ByteArray) =
        saveGalleryImageUseCase (image)

    override fun onCleared () =
        disposable.clear ()
}

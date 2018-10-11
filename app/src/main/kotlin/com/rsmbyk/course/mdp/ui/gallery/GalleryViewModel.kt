package com.rsmbyk.course.mdp.ui.gallery

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import com.rsmbyk.course.mdp.domain.usecase.SaveImageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GalleryViewModel (
    private val getImagesUseCase: GetImagesUseCase,
    private val saveImageUseCase: SaveImageUseCase)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val images = MutableLiveData<List<ByteArray>> ()

    fun getImages () {
        if (images.value != null)
            images.value = images.value
        else
            disposable.add (getImagesUseCase ()
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (images::setValue))
    }

    fun saveImage (image: ByteArray) =
        saveImageUseCase (image)

    override fun onCleared () {
        disposable.clear ()
    }
}

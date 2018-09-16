package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class CameraViewModel (
    val imageDirectory: File,
    getImagesUseCase: GetImagesUseCase,
    private val getCapturedImageUseCase: GetCapturedImageUseCase)
        : ViewModel () {

    private val imageList = mutableListOf<File> ()
    val imageListAdapter = GridImageAdapter (imageList)

    init {
        getImagesUseCase (imageDirectory)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe { imageList.addAll (it) }
    }

    fun addCapturedImage () {
        getCapturedImageUseCase (imageDirectory)
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe {
                imageList.add (0, it)
                imageListAdapter.notifyItemInserted (0)
            }
    }
}

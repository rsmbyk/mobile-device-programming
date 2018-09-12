package com.rsmbyk.course.mdp.ui.camera

import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.usecase.GetCapturedImageUseCase
import com.rsmbyk.course.mdp.domain.usecase.GetImagesUseCase
import java.io.File

class CameraViewModel (
    val imageDirectory: File,
    getImagesUseCase: GetImagesUseCase,
    private val getCapturedImageUseCase: GetCapturedImageUseCase)
        : ViewModel () {

    private val imageList: MutableList<File> =
        getImagesUseCase (imageDirectory).toMutableList ()

    val imageListAdapter =
        GridImageAdapter (imageList)

    fun addCapturedImage () {
        imageList.add (0, getCapturedImageUseCase (imageDirectory))
        imageListAdapter.notifyItemInserted (0)
    }
}

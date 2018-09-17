package com.rsmbyk.course.mdp.ui.database

import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.usecase.GetUploadedImageUseCase
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.ui.networking.UploadImageListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DatabaseViewModel (
    private val mapper: Mapper<UploadImage, UploadImageModel>,
    private val getUploadedImageUseCase: GetUploadedImageUseCase)
        : ViewModel () {

    private val uploadList = mutableListOf<UploadImageModel> ()
    val uploadListAdapter = UploadImageListAdapter (uploadList)

    fun getUploadedImages () {
        getUploadedImageUseCase ()
            .map { it.map (mapper::mapToModel) }
            .subscribeOn (Schedulers.io ())
            .observeOn (AndroidSchedulers.mainThread ())
            .subscribe (::addAll)
    }

    private fun addAll (uploadedImages: List<UploadImageModel>) {
        uploadList.clear ()
        uploadList.addAll (uploadedImages)
        uploadListAdapter.notifyDataSetChanged ()
    }
}

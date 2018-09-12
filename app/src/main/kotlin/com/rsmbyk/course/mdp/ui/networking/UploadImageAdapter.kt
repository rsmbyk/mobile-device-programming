package com.rsmbyk.course.mdp.ui.networking

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.model.UploadImageModel

class UploadImageAdapter (private val uploadImages: List<UploadImageModel>)
    : RecyclerView.Adapter<UploadImageViewHolder> () {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): UploadImageViewHolder =
        UploadImageViewHolder (
            LayoutInflater
                .from (parent.context)
                .inflate (R.layout.viewholder_image_list, parent, false)
        )

    override fun onBindViewHolder (holder: UploadImageViewHolder, position: Int) =
        holder.bind (uploadImages[position])

    override fun getItemCount(): Int =
        uploadImages.size
}

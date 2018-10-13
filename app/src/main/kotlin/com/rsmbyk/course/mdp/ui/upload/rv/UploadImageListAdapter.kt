package com.rsmbyk.course.mdp.ui.upload.rv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R.layout
import com.rsmbyk.course.mdp.model.UploadImageModel

class UploadImageListAdapter (
    private val uploadImages: List<UploadImageModel>,
    private val onClickListener: (index: Int) -> Unit,
    private val onLongClickListener: (index: Int) -> Boolean)
        : RecyclerView.Adapter<UploadImageListViewHolder> () {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): UploadImageListViewHolder {
        return UploadImageListViewHolder (
            LayoutInflater
                .from (parent.context)
                .inflate (layout.viewholder_upload_image, parent, false)
        )
    }

    override fun onBindViewHolder (holder: UploadImageListViewHolder, position: Int) {
        holder.bind (uploadImages[position], onClickListener, onLongClickListener)
    }

    override fun getItemCount (): Int =
        uploadImages.size

    fun updateImage (index: Int?) {
        index?.also (::notifyItemChanged)
    }
}

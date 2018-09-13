package com.rsmbyk.course.mdp.ui.networking

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.setVisible
import com.rsmbyk.course.mdp.model.UploadListImageModel
import com.rsmbyk.course.mdp.model.UploadListImageModel.UploadProgress
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image_list.*
import java.io.File

class UploadImageListViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (file: File) {
        Glide
            .with (itemView)
            .load (file)
            .apply (RequestOptions ().placeholder (R.drawable.ic_broken_image))
            .into (preview)
        filename.text = file.nameWithoutExtension
    }

    fun bind (item: UploadListImageModel) {
        bind (item.file)
        success_indicator.setVisible (item.uploadProgress == UploadProgress.SUCCESS)
        uploading_indicator.setVisible (item.uploadProgress == UploadProgress.UPLOADING)
        elapsed_time.setVisible (item.uploadProgress == UploadProgress.SUCCESS)
        elapsed_time.text = "${item.elapsedTime} second(s)"
    }
}

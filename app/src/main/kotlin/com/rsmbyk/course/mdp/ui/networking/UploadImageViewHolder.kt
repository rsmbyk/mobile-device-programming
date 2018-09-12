package com.rsmbyk.course.mdp.ui.networking

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.model.UploadImageModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image_list.*
import java.io.File

class UploadImageViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (file: File) {
        Glide
            .with (itemView)
            .load (file)
            .apply (RequestOptions ().placeholder (R.drawable.ic_image_placeholder))
            .into (preview)
        filename.text = file.nameWithoutExtension
    }

    fun bind (item: UploadImageModel) {
        bind (item.file)
        uploaded.visibility = if (item.uploaded) View.VISIBLE else View.GONE
    }
}

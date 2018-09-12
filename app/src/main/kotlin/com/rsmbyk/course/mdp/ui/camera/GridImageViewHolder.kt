package com.rsmbyk.course.mdp.ui.camera

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image_grid.*
import java.io.File

class GridImageViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (file: File) {
        Glide
            .with (itemView)
            .load (file)
            .apply (RequestOptions ().placeholder (R.drawable.ic_broken_image))
            .into (image)
    }
}

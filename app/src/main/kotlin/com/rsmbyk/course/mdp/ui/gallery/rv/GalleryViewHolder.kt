package com.rsmbyk.course.mdp.ui.gallery.rv

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_gallery.*

class GalleryViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (data: ByteArray) {
        Glide
            .with (itemView)
            .load (data)
            .apply (RequestOptions ().placeholder (R.drawable.ic_broken_image))
            .into (image)
    }
}

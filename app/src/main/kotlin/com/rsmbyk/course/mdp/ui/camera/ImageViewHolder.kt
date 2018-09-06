package com.rsmbyk.course.mdp.ui.camera

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image.*
import java.io.File

class ImageViewHolder (override val containerView: View): RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (file: File) {
        Glide
            .with (containerView)
            .load (file)
            .apply (RequestOptions ()
                .override (200)
                .placeholder (R.drawable.ic_image_placeholder)
                .downsample (DownsampleStrategy.CENTER_OUTSIDE))
            .into (image)
    }
}

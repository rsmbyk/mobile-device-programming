package com.rsmbyk.course.mdp.ui.upload.rv

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.util.setVisible
import com.rsmbyk.course.mdp.model.UploadImageModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_upload_image.*

class UploadImageListViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (item: UploadImageModel, onClickListener: (index: Int) -> Unit, onLongClickListener: (index: Int) -> Boolean) {
        item.apply {
            Glide
                .with (itemView)
                .load (image ?: R.drawable.ic_face)
                .apply (RequestOptions ().placeholder (R.drawable.ic_broken_image))
                .into (preview)

            name.text = item.text
            elapsed_time.text =
                if (elapsedTime == null) ""
                else String.format (containerView.context.getString (R.string.upload_image_elapsed_time), elapsedTime)

            uploading_indicator.setVisible (state == UploadImageModel.State.UPLOADING)
            success_indicator.setVisible (state == UploadImageModel.State.SUCCESS)
            error_indicator.setVisible (state == UploadImageModel.State.FAILED)

            containerView.setOnClickListener { onClickListener (index) }
            containerView.setOnLongClickListener { onLongClickListener (index) }
        }
    }
}

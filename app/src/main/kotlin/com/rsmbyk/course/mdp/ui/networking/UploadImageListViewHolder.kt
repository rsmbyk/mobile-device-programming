package com.rsmbyk.course.mdp.ui.networking

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.setVisible
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.model.UploadImageModel.UploadProgress.IDLE
import com.rsmbyk.course.mdp.model.UploadImageModel.UploadProgress.SUCCESS
import com.rsmbyk.course.mdp.model.UploadImageModel.UploadProgress.UPLOADING
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image_list.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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

    fun bind (item: UploadImageModel) {
        item.apply {
            bind (file)
            upload_time.setVisible (uploadTime >= 0)
            upload_time.text = uploadTime.toDateTime ()
            indicators.setVisible (uploadProgress != IDLE || elapsedTime > -1f)
            metadatas.setVisible (elapsedTime > -1f)
            id.setVisible (uploadProgress == IDLE)
            id.text = index.toString ()
            elapsed_time.text = "${item.elapsedTime} s"
            success_indicator.setVisible (uploadProgress == SUCCESS)
            uploading_indicator.setVisible (uploadProgress == UPLOADING)
        }
    }

    private fun Long.toDateTime (): String {
        val calendar = Calendar.getInstance ()
//        "dd MMMM yyyy, HH:MM"
        calendar.timeInMillis = this
        val sdf = SimpleDateFormat.getDateTimeInstance ()
        return sdf.format (calendar.time)
    }
}

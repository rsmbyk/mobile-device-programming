package com.rsmbyk.course.mdp.ui.database.rv

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.model.UploadImageModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_datatabase.*
import java.text.SimpleDateFormat
import java.util.*

class DatabaseViewHolder (override val containerView: View)
    : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (item: UploadImageModel) {
        item.apply {
            Glide
                .with (itemView)
                .load (image ?: R.drawable.ic_face)
                .apply (RequestOptions ().placeholder (R.drawable.ic_broken_image))
                .into (preview)

            name.text = text
            elapsed_time.text = String.format (containerView.context.getString (R.string.upload_image_elapsed_time), elapsedTime!!)
            upload_timestamp.text = timestamp!!.toDateTime ()
        }
    }

    private fun Long.toDateTime (): String {
        val calendar = Calendar.getInstance ()
        //  "dd MMMM yyyy, HH:MM"
        calendar.timeInMillis = this
        val sdf = SimpleDateFormat.getDateTimeInstance ()
        return sdf.format (calendar.time)
    }
}

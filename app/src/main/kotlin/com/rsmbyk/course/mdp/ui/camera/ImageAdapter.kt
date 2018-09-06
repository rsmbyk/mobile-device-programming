package com.rsmbyk.course.mdp.ui.camera

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import java.io.File

class ImageAdapter (private var images: MutableList<File>): RecyclerView.Adapter<ImageViewHolder> () {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder (
            LayoutInflater
                .from (parent.context)
                .inflate (R.layout.viewholder_image, parent, false))

    override fun onBindViewHolder (holder: ImageViewHolder, position: Int) =
        holder.bind (images[position])

    override fun getItemCount (): Int =
        images.size

    fun setData (data: MutableList<File>) {
        images = data
        notifyDataSetChanged ()
    }
}

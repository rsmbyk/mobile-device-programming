package com.rsmbyk.course.mdp.ui.gallery.rv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R.layout

class GalleryAdapter: RecyclerView.Adapter<GalleryViewHolder> () {

    private val images = mutableListOf<ByteArray>()

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            LayoutInflater
                .from (parent.context)
                .inflate (layout.viewholder_gallery, parent, false)
        )
    }

    override fun onBindViewHolder (holder: GalleryViewHolder, position: Int) =
        holder.bind (images[position])

    override fun getItemCount (): Int =
        images.size

    fun setImages (images: List<ByteArray>) =
        images.forEach (::addImage)

    fun addImage (image: ByteArray) {
        images.add (0, image)
        notifyItemInserted (0)
    }
}

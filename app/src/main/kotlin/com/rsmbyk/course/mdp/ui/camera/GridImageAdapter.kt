package com.rsmbyk.course.mdp.ui.camera

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import java.io.File

class GridImageAdapter (private val images: List<File>)
    : RecyclerView.Adapter<GridImageViewHolder> () {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): GridImageViewHolder =
        GridImageViewHolder (
            LayoutInflater
                .from (parent.context)
                .inflate (R.layout.viewholder_image_grid, parent, false))

    override fun onBindViewHolder (holder: GridImageViewHolder, position: Int) =
        holder.bind (images[position])

    override fun getItemCount (): Int =
        images.size
}

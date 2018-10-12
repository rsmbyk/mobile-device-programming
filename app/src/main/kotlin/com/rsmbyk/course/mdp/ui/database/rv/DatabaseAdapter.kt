package com.rsmbyk.course.mdp.ui.database.rv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.model.UploadImageModel

class DatabaseAdapter (private val uploadImages: List<UploadImageModel>): RecyclerView.Adapter<DatabaseViewHolder> () {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): DatabaseViewHolder {
        return DatabaseViewHolder (
            LayoutInflater
                .from (parent.context)
                .inflate (R.layout.viewholder_datatabase, parent, false))
    }

    override fun getItemCount (): Int =
        uploadImages.size

    override fun onBindViewHolder (holder: DatabaseViewHolder, position: Int) =
        holder.bind (uploadImages[position])
}

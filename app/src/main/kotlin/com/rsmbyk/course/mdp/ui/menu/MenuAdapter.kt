package com.rsmbyk.course.mdp.ui.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R

class MenuAdapter (
    private val menus: List<String>, private val onMenuItemClickListener: (position: Int) -> Unit)
        : RecyclerView.Adapter<MenuViewHolder> () {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder (
            LayoutInflater
                .from (parent.context)
                .inflate (R.layout.viewholder_menu, parent, false),
            onMenuItemClickListener)

    override fun getItemCount (): Int =
        menus.size

    override fun onBindViewHolder (holder: MenuViewHolder, position: Int) =
        holder.bind (menus[position])
}

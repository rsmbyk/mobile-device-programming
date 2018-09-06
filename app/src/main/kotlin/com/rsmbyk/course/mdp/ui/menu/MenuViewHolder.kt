package com.rsmbyk.course.mdp.ui.menu

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_menu.*

class MenuViewHolder (
    override val containerView: View, private val onMenuItemClickListener: (position: Int) -> Unit)
        : RecyclerView.ViewHolder (containerView), LayoutContainer {

    fun bind (menuText: String) {
        text.text = menuText
        text.setOnClickListener { onMenuItemClickListener (adapterPosition) }
    }
}

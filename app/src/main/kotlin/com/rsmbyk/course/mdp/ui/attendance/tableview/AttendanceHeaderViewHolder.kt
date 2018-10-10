package com.rsmbyk.course.mdp.ui.attendance.tableview

import android.view.View
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_attendance_cell.*

class AttendanceHeaderViewHolder (override val containerView: View)
    : AbstractViewHolder (containerView), LayoutContainer {

    fun bind (value: String) {
        text.text = value
    }
}

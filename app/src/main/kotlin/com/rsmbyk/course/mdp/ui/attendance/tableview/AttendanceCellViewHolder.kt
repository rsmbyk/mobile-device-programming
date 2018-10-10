package com.rsmbyk.course.mdp.ui.attendance.tableview

import android.view.View
import android.widget.LinearLayout
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_attendance_cell.*

class AttendanceCellViewHolder (override val containerView: View)
    : AbstractViewHolder (containerView), LayoutContainer {

    fun bind (column: Int, value: String) {
        text.text = value
        containerView.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        containerView.requestLayout ()
    }
}

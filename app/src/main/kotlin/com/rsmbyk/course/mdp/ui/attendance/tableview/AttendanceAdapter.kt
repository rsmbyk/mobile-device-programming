package com.rsmbyk.course.mdp.ui.attendance.tableview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.rsmbyk.course.mdp.R

class AttendanceAdapter (private val context: Context)
    : AbstractTableAdapter<String, String, String> (context) {

    override fun onCreateColumnHeaderViewHolder (parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        return AttendanceHeaderViewHolder (
            LayoutInflater
                .from (context)
                .inflate (R.layout.viewholder_attendance_column_header, parent, false)
        )
    }

    override fun onCreateRowHeaderViewHolder (parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        return AttendanceHeaderViewHolder (
            LayoutInflater
                .from (context)
                .inflate (R.layout.viewholder_attendance_row_header, parent, false)
        )
    }

    override fun onCreateCellViewHolder (parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        return AttendanceCellViewHolder (
            LayoutInflater
                .from (context)
                .inflate (R.layout.viewholder_attendance_cell, parent, false)
        )
    }

    override fun onCreateCornerView (): View {
        return LayoutInflater
            .from (context)
            .inflate (R.layout.viewholder_attendance_corner_view, null)
    }

    override fun onBindColumnHeaderViewHolder (holder: AbstractViewHolder?, columnHeaderItemModel: Any?, columnPosition: Int) =
        (holder as AttendanceHeaderViewHolder).bind (columnHeaderItemModel as String)

    override fun onBindRowHeaderViewHolder (holder: AbstractViewHolder?, rowHeaderItemModel: Any?, rowPosition: Int) =
        (holder as AttendanceHeaderViewHolder).bind (rowHeaderItemModel as String)

    override fun onBindCellViewHolder(holder: AbstractViewHolder?, cellItemModel: Any?, columnPosition: Int, rowPosition: Int) =
        (holder as AttendanceCellViewHolder). bind (columnPosition, cellItemModel as String)

    override fun getColumnHeaderItemViewType(position: Int): Int =
        0

    override fun getRowHeaderItemViewType(position: Int): Int =
        0

    override fun getCellItemViewType (position: Int): Int =
        0
}

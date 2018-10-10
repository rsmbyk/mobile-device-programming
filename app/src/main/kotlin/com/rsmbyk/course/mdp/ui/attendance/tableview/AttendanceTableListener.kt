package com.rsmbyk.course.mdp.ui.attendance.tableview

import android.support.v7.widget.RecyclerView.ViewHolder
import com.evrencoskun.tableview.listener.ITableViewListener

class AttendanceTableListener (private val onItemLongClicked: (row: Int) -> Unit): ITableViewListener {

    override fun onCellLongPressed (cellView: ViewHolder, column: Int, row: Int) =
        onItemLongClicked (row)

    override fun onCellClicked (cellView: ViewHolder, column: Int, row: Int) {}
    override fun onColumnHeaderClicked (columnHeaderView: ViewHolder, column: Int) {}
    override fun onColumnHeaderLongPressed (columnHeaderView: ViewHolder, column: Int) {}
    override fun onRowHeaderLongPressed (rowHeaderView: ViewHolder, row: Int) {}
    override fun onRowHeaderClicked (rowHeaderView: ViewHolder, row: Int) {}
}

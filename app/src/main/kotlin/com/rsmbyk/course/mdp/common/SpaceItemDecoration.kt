package com.rsmbyk.course.mdp.common

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.util.DisplayMetrics
import android.view.View

class SpaceItemDecoration (
        context: Context,
        spaceInDp: Int,
        private val spanCount: Int = 1,
        private val edgeSpace: Boolean = false)
    : RecyclerView.ItemDecoration () {

    private val space: Int =
        spaceInDp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

    override fun getItemOffsets (outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets (outRect, view, parent, state)

        val position = parent.getChildLayoutPosition (view)

        outRect.top = setOutRect (position < spanCount)

        if (spanCount > 1) {
            outRect.left = setOutRect (position % spanCount == 0)
            outRect.right = setOutRect (position % spanCount == spanCount - 1)
        }

        // distance to the edge of spanCount.
        // if spanCount is 4 and the position
        // of current item is 6, the odd will be 2 (8 - 6).
        val odd = parent.adapter!!.itemCount % spanCount
        outRect.bottom = setOutRect (position >= parent.adapter!!.itemCount - odd)
    }

    private fun setOutRect (edgeCondition: Boolean): Int =
        if (edgeCondition) {
            if (edgeSpace) space
            else 0
        } else {
            space / 2
        }
}

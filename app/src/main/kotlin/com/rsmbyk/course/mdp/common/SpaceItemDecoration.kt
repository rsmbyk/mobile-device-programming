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

        if (position < spanCount) {
            if (edgeSpace)
                outRect.top = space
        } else {
            outRect.top = space / 2
        }

        if (spanCount > 1) {
            if (position % spanCount == 0) {
                if (edgeSpace)
                    outRect.left = space
            } else {
                outRect.left = space / 2
            }

            if (position % spanCount == spanCount - 1) {
                if (edgeSpace)
                    outRect.right = space
            } else {
                outRect.right = space / 2
            }
        }

        // distance to the edge of spanCount.
        // if spanCount is 4 and the position
        // of current item is 6, the odd will be 2 (8 - 6).
        val odd = parent.adapter!!.itemCount % spanCount

        if (position >= parent.adapter!!.itemCount - odd) {
            if (edgeSpace)
                outRect.bottom = space
        } else {
            outRect.bottom = space / 2
        }
    }

    private fun setOutRect (edgeCondition: Boolean) =
        (if (edgeSpace) space else 0) / (if (edgeCondition) 1 else 2)
}

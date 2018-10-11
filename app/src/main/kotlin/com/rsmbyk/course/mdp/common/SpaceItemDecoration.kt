package com.rsmbyk.course.mdp.common

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.util.DisplayMetrics
import android.view.View
import com.rsmbyk.course.mdp.R

class SpaceItemDecoration (
    context: Context,
    spaceInDp: Int? = null,
    private val spanCount: Int = 1,
    private val edgeSpace: Boolean = false)
        : RecyclerView.ItemDecoration () {

    private val space: Int

    init {
        val finalSpaceInDp = (spaceInDp ?: context.resources.getInteger (R.integer.material_imagelists_padding))
        space = finalSpaceInDp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    // kotlin modulo operation does not work as expected with negative dividend
    // https://youtrack.jetbrains.com/issue/KT-26751
    private fun Int.negativeMod (divisor: Int): Int {
        var quotient = this / divisor
        if (this % divisor != 0)
            quotient -= 1
        return this - (divisor * quotient)
    }


    override fun getItemOffsets (outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets (outRect, view, parent, state)

        val position = parent.getChildLayoutPosition (view)

        outRect.top = setOutRect (position < spanCount)

        if (spanCount > 1) {
            outRect.left = setOutRect (position % spanCount == 0)
            outRect.right = setOutRect (position % spanCount == spanCount - 1)
        }

        val count = state.itemCount
        val lastRowRemainder = (-count).negativeMod (spanCount)
        val lastRowIndex = count - spanCount + lastRowRemainder
        outRect.bottom = setOutRect (position >= lastRowIndex)
    }

    private fun setOutRect (edgeCondition: Boolean): Int =
        if (edgeCondition) {
            if (edgeSpace) space
            else 0
        } else {
            space / 2
        }
}

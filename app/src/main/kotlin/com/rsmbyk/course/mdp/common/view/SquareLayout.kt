package com.rsmbyk.course.mdp.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.view.SquareLayout.Pivot

class SquareLayout (context: Context, attrs: AttributeSet): RelativeLayout (context, attrs) {

    private val pivot: Pivot

    init {
        val styledAttributes: TypedArray = context.obtainStyledAttributes (attrs, R.styleable.SquareLayout, 0, 0)
        val pivotValue: Int = styledAttributes.getInteger (R.styleable.SquareImageView_pivot, -1)
        assert (pivotValue != -1) { "Pivot must be set" }
        pivot = Pivot.values ()[pivotValue]
        styledAttributes.recycle ()
        post {
            getMeasuredPivot ().let {
                layoutParams.width = it
                layoutParams.height = it
                requestLayout ()
            }
        }
    }

    private fun getMeasuredPivot (): Int {
        return when (pivot) {
            Pivot.WIDTH -> measuredWidth
            Pivot.HEIGHT -> measuredHeight
            Pivot.SMALLEST -> minOf (measuredWidth, measuredHeight)
            Pivot.LARGEST -> maxOf (measuredWidth, measuredHeight)
        }
    }

    enum class Pivot {
        WIDTH,
        HEIGHT,
        SMALLEST,
        LARGEST
    }
}

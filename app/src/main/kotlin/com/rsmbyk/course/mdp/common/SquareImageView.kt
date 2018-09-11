package com.rsmbyk.course.mdp.common

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import com.rsmbyk.course.mdp.R

class SquareImageView (context: Context, attrs: AttributeSet): ImageView (context, attrs) {

    private val pivot: Pivot

    init {
        val styledAttributes: TypedArray = context.obtainStyledAttributes (attrs, R.styleable.SquareImageView, 0, 0)
        val pivotValue: Int = styledAttributes.getInteger (R.styleable.SquareImageView_pivot, -1)
        assert (pivotValue != -1) { "Pivot must be set" }
        pivot = Pivot.values ()[pivotValue]
        styledAttributes.recycle ()
    }

    public override fun onMeasure (widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure (widthMeasureSpec, when (pivot) {
            Pivot.WIDTH -> widthMeasureSpec
            Pivot.HEIGHT -> heightMeasureSpec
            Pivot.SMALLEST -> minOf (widthMeasureSpec, heightMeasureSpec)
            Pivot.LARGEST -> maxOf (widthMeasureSpec, heightMeasureSpec)
        })
    }

    enum class Pivot {
        WIDTH,
        HEIGHT,
        SMALLEST,
        LARGEST
    }
}

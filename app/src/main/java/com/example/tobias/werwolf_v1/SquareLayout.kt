package com.example.tobias.werwolf_v1

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SquareLayout : LinearLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onMeasure(widthMaesureSpec: Int, hightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMaesureSpec)
        var height = MeasureSpec.getSize(hightMeasureSpec)
        if (width > (height + 0.5).toInt()) {
            width = (height + 0.5).toInt()
        } else {
            height = (width + 0.5).toInt()
        }
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }
}
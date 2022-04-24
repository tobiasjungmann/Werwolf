package com.example.tobias.werwolf_v1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLayout extends LinearLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMaesureSpec, int hightMeasureSpec){
        int width = MeasureSpec.getSize(widthMaesureSpec);
        int height = MeasureSpec.getSize(hightMeasureSpec);


        if (width > (int)((height) + 0.5)) {
            width = (int)((height) + 0.5);
        } else {
            height = (int)((width) + 0.5);
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        );
    }
}
package com.roaim.kotlinpinboard.utils;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class ThumbView extends AppCompatImageView {
    public ThumbView(Context context) {
        super(context);
    }

    public ThumbView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}

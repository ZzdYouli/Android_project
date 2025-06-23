package com.example.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;

public class TagCloudView extends ViewGroup {

    private int horizontalSpacing = 20; // 默认左右边距
    private int verticalSpacing = 20;   // 默认上下边距

    public TagCloudView(Context context) {
        super(context);
        init(null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TagCloudView);
            horizontalSpacing = a.getDimensionPixelSize(R.styleable.TagCloudView_horizontalSpacing, horizontalSpacing);
            verticalSpacing = a.getDimensionPixelSize(R.styleable.TagCloudView_verticalSpacing, verticalSpacing);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int usedWidth = 0;
        int usedHeight = verticalSpacing;
        int rowHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            if (usedWidth + child.getMeasuredWidth() + horizontalSpacing > width) {
                usedWidth = 0;
                usedHeight += rowHeight + verticalSpacing;
                rowHeight = 0;
            }

            usedWidth += child.getMeasuredWidth() + horizontalSpacing;
            rowHeight = Math.max(rowHeight, child.getMeasuredHeight());
        }

        usedHeight += rowHeight + verticalSpacing;
        setMeasuredDimension(width, usedHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int x = 0;
        int y = verticalSpacing;
        int rowHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (x + childWidth + horizontalSpacing > width) {
                x = 0;
                y += rowHeight + verticalSpacing;
                rowHeight = 0;
            }

            child.layout(x + horizontalSpacing, y, x + horizontalSpacing + childWidth, y + childHeight);
            x += childWidth + horizontalSpacing;
            rowHeight = Math.max(rowHeight, childHeight);
        }
    }
}

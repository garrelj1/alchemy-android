package com.garrell.co.alchemytcg.card.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CardHandLayoutManager extends ViewGroup {

    private static final int MAX_CARDS = 7;
    private static final int CARD_MARGIN = 20;

    public CardHandLayoutManager(@NonNull Context context) {
        super(context);
    }

    public CardHandLayoutManager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardHandLayoutManager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int childWidth = (width - (MAX_CARDS + 1) * CARD_MARGIN) / MAX_CARDS;
        int childHeight = height - 2 * CARD_MARGIN;

        // Measure each child with the calculated size
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft = CARD_MARGIN;
        int childRight = getChildAt(0).getMeasuredWidth() + CARD_MARGIN;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childLeft, CARD_MARGIN, childRight, bottom - CARD_MARGIN);
            childLeft += child.getWidth() + CARD_MARGIN;
            childRight += child.getWidth() + CARD_MARGIN;
        }
    }

    public void addCardView(View cardView) {
        if (getChildCount() < MAX_CARDS) {
            addView(cardView);
            requestLayout();
        }
    }

    public void removeCardView(View cardView) {
        removeView(cardView);
        requestLayout();
    }

}

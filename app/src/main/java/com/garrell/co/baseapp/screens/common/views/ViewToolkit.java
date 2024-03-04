package com.garrell.co.baseapp.screens.common.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import com.garrell.co.baseapp.screens.common.views.res.ResourcesFacade;

public class ViewToolkit {

    private final ResourcesFacade resources;

    public ViewToolkit(Context context) {
        this.resources = new ResourcesFacade(context);
    }

    public int dpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                resources.getDisplayMetrics()
        );
    }

    public String getDefaultAttributeNamespace() {
        return "http://schemas.android.com/apk/res-auto";
    }

    public int getTextHeight(String text, Paint textPaint) {
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds.height();
    }

}

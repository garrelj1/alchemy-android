package com.garrell.co.alchemytcg.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import timber.log.Timber;

public class DragableCardView extends AppCompatTextView implements View.OnTouchListener {

    private int CARD_HITBOX_HEIGHT = 10;
    private int CARD_HITBOX_WIDTH = 10;

    private float dX = 0;
    private float dY = 0;

    public DragableCardView(Context context) {
        super(context);
        init();
    }


    public DragableCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragableCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setActivated(true);
        setClickable(true);
        setFocusable(true);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Timber.d("onTouchEvent");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                Timber.d("onTouchEvent action down, dX: " + dX + " dY: " + dY);
                break;

            case MotionEvent.ACTION_MOVE:
                Timber.d("onTouchEvent move, dX: " + dX + " dY: " + dY);
                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }

        return true;
    }

    private InnerHitbox getInnerHitbox() {
        float centerX =  getRight() - getLeft();
        float centerY = getBottom() - getTop();

        float left = centerX - CARD_HITBOX_WIDTH;
        float right = centerX + CARD_HITBOX_WIDTH;
        float top = centerY - CARD_HITBOX_HEIGHT;
        float bottom = centerY + CARD_HITBOX_HEIGHT;

        return new InnerHitbox(left, right, top, bottom);
    }

    public boolean isWithinBox(float left, float right, float top, float bottom) {
        InnerHitbox hitbox = getInnerHitbox();

        if (hitbox.left > right) {
            return false;
        }

        if (hitbox.right < left) {
            return false;
        }

        if (hitbox.top < bottom) {
            return false;
        }

        if (hitbox.bottom > top) {
            return false;
        }

        return true;
    }

    private static class InnerHitbox {
        float left;
        float right;
        float top;
        float bottom;

        InnerHitbox(float left, float right, float top, float bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }
}

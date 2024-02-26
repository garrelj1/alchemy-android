package com.garrell.co.alchemytcg.card;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import timber.log.Timber;

public class DragableCardView extends AppCompatTextView implements View.OnTouchListener {


    public interface Listener {
        void onCardPlaced(DragableCardView card, Rect hitbox);
    }

    private Listener listener;

    private int CARD_HITBOX_HEIGHT = 10;
    private int CARD_HITBOX_WIDTH = 10;

    private float dX = 0;
    private float dY = 0;

    private float startingLocationX = -1;
    private float startingLocationY = -1;

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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (startingLocationX == -1) {
            startingLocationX = left;
        }
        if (startingLocationY == -1) {
            startingLocationY = top;
        }
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
                Timber.d("onTouchEvent final position: %s", getInnerHitboxInWindow());
                break;
            case MotionEvent.ACTION_UP:
                listener.onCardPlaced(this, getInnerHitboxInWindow());
                break;
            default:
                return false;
        }

        return true;
    }

    private Rect getInnerHitboxInWindow() {
        int[] xy = new int[2];
        getLocationInWindow(xy);
        int hitboxLeft = (xy[0] + getWidth() / 2) - CARD_HITBOX_WIDTH;
        int hitboxRight = (xy[0] + getWidth() / 2) + CARD_HITBOX_WIDTH;
        int hitboxTop = (xy[1] + getHeight() / 2) + CARD_HITBOX_HEIGHT;
        int hitboxBottom = (xy[1] + getHeight() / 2) - CARD_HITBOX_HEIGHT;

        return new Rect(hitboxLeft, hitboxTop, hitboxRight, hitboxBottom);
    }

    private Rect getInnerHitbox() {
        int hitboxLeft = (getLeft() + getWidth() / 2) - CARD_HITBOX_WIDTH;
        int hitboxRight = (getRight() - getWidth() / 2) + CARD_HITBOX_WIDTH;
        int hitboxTop = (getTop() - getHeight() / 2) + CARD_HITBOX_HEIGHT;
        int hitboxBottom = (getBottom() + getHeight() / 2) - CARD_HITBOX_HEIGHT;

        return new Rect(hitboxLeft, hitboxTop, hitboxRight, hitboxBottom);
    }

    public void resetLocation() {
        Timber.d("Resetting location to x: " + startingLocationX + " y: " + startingLocationY);
        animate().x(startingLocationX)
                .y(startingLocationY)
                .setDuration(500)
                .start();
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    public void unregisterListener() {
        this.listener = null;
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

package com.garrell.co.alchemytcg.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.garrell.co.alchemytcg.card.view.PlayableCardView;

public class CardSlotView extends AppCompatTextView {

    private Paint borderPaint;
    private PlayableCardView card;

    public CardSlotView(Context context) {
        super(context);
        init();
    }

    public CardSlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardSlotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);

        borderPaint = new Paint();
        borderPaint.setColor(Color.BLUE);
        borderPaint.setStrokeWidth(5);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSlot(canvas);
    }

    private void drawSlot(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint);
    }

    public Rect getHitbox() {
        return new Rect(getLeft(), getTop(), getRight(), getBottom());
    }

    public boolean isEmpty() {
        return card == null;
    }

    public void setCardInSlot(PlayableCardView card) {
        this.card = card;
    }

    public PlayableCardView getCardInSlot() {
        return this.card;
    }

    public void empty() {
        this.card = null;
    }
}

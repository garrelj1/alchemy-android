package com.garrell.co.alchemytcg.screens.game;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.garrell.co.alchemytcg.board.CardSlotView;
import com.garrell.co.alchemytcg.card.DragableCardView;
import com.garrell.co.baseapp.R;
import com.garrell.co.baseapp.screens.common.mvcviews.BaseObservableViewMvc;

import java.util.List;

import timber.log.Timber;

public class GameViewMvcImpl extends BaseObservableViewMvc<GameViewMvc.Listener>
        implements GameViewMvc, DragableCardView.Listener {

    private final DragableCardView card;
    private final CardSlotView leftSlot;
    private final CardSlotView centerSlot;
    private final CardSlotView rightSlot;

    @SuppressLint("ClickableViewAccessibility")
    public GameViewMvcImpl(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_game, container, false));
        card = getRootView().findViewById(R.id.card);
        card.registerListener(this);
        leftSlot = getRootView().findViewById(R.id.left_slot);
        centerSlot = getRootView().findViewById(R.id.center_slot);
        rightSlot = getRootView().findViewById(R.id.right_slot);
    }

    @Override
    public void addCardToHand(String cardName) {
        card.setText(cardName);
    }

    public boolean isCardWithinSlot(Rect card, Rect slot) {
        if (card.left > slot.right) {
            Timber.d("Card " + card.left + " is to the right of the slot " + slot.right);
            return false;
        }

        if (card.right < slot.left) {
            Timber.d("Card " + card.right + " is to the left of the slot " + slot.left);
            return false;
        }

        if (card.top < slot.bottom) {
            Timber.d("Card " + card.top + " is below the slot " + slot.bottom);
            return false;
        }

        if (card.bottom > slot.top) {
            Timber.d("Card " + card.bottom + " is above the slot " + slot.top);
            return false;
        }

        Timber.d("Card is within the slot");
        return true;
    }

    @Override
    public void onCardPlaced(Rect cardHitbox) {
        if (isCardWithinSlot(cardHitbox, leftSlot.getHitbox()) ||
            isCardWithinSlot(cardHitbox, centerSlot.getHitbox()) ||
            isCardWithinSlot(cardHitbox, rightSlot.getHitbox())
        ) {
            for (Listener l : getListeners()) {
                l.onCardPlayed(card.getText().toString());
            }
        } else {
            card.resetLocation();
        }
    }

}

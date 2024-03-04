package com.garrell.co.alchemytcg.screens.game;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.garrell.co.alchemytcg.board.CardSlotView;
import com.garrell.co.alchemytcg.card.Describable;
import com.garrell.co.alchemytcg.card.view.DragableCardView;
import com.garrell.co.baseapp.R;
import com.garrell.co.baseapp.screens.common.mvcviews.BaseObservableViewMvc;

public class GameViewMvcImpl extends BaseObservableViewMvc<GameViewMvc.Listener>
        implements GameViewMvc, DragableCardView.Listener {

    private final CardHandLayoutManager cardHandLayoutManager;
    private final CardSlotView leftSlot;
    private final CardSlotView centerSlot;
    private final CardSlotView rightSlot;

    @SuppressLint("ClickableViewAccessibility")
    public GameViewMvcImpl(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_game, container, false));

        cardHandLayoutManager = getRootView().findViewById(R.id.card_hand);

        DragableCardView cardView = new DragableCardView(layoutInflater.getContext());
        cardView.setWidth(45);
        cardView.setWidth(60);
        cardView.setText("test");
        cardView.registerListener(this);
        cardHandLayoutManager.addCardView(cardView);

        leftSlot = getRootView().findViewById(R.id.left_slot);
        centerSlot = getRootView().findViewById(R.id.center_slot);
        rightSlot = getRootView().findViewById(R.id.right_slot);
    }

    @Override
    public void onCardPlaced(DragableCardView card, Rect cardHitbox) {
        if (cardHitbox.intersect(leftSlot.getHitbox())) {
            leftSlot.setEmpty(false);
        }

        if (cardHitbox.intersect(leftSlot.getHitbox()) && leftSlot.isEmpty() ||
            cardHitbox.intersect(centerSlot.getHitbox()) && centerSlot.isEmpty() ||
            cardHitbox.intersect(rightSlot.getHitbox()) && rightSlot.isEmpty()
        ) {
            for (Listener l : getListeners()) {
                l.onCardPlayed(card.getText().toString());
            }
        } else {
            card.resetLocation();
        }
    }

    @Override
    public void addCardToHand(Describable card) {
    }

}

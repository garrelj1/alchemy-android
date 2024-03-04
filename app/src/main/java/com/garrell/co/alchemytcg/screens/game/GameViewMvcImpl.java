package com.garrell.co.alchemytcg.screens.game;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Space;

import com.garrell.co.alchemytcg.board.CardSlotView;
import com.garrell.co.alchemytcg.card.DescribableCard;
import com.garrell.co.alchemytcg.card.view.DragableCardView;
import com.garrell.co.baseapp.R;
import com.garrell.co.baseapp.screens.common.mvcviews.BaseObservableViewMvc;

public class GameViewMvcImpl extends BaseObservableViewMvc<GameViewMvc.Listener>
        implements GameViewMvc, DragableCardView.Listener {

    private final Space cardHandBaseline;
    private final CardSlotView operatorSlot;
    private final CardSlotView numberSlot;
    private final CardSlotView playerActionSlot;

    @SuppressLint("ClickableViewAccessibility")
    public GameViewMvcImpl(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_game, container, false));

        cardHandBaseline = getRootView().findViewById(R.id.card_hand_bottom);

        DragableCardView cardView = new DragableCardView(layoutInflater.getContext());
        cardView.setWidth(45);
        cardView.setWidth(60);
        cardView.setText("test");
        cardView.registerListener(this);

        operatorSlot = getRootView().findViewById(R.id.operator_slot);
        numberSlot = getRootView().findViewById(R.id.number_slot);
        playerActionSlot = getRootView().findViewById(R.id.player_action_slot);
    }

    @Override
    public void onCardPlaced(DragableCardView card, Rect cardHitbox) {
        if (cardHitbox.intersect(operatorSlot.getHitbox())) {
            operatorSlot.setEmpty(false);
        }

        if (cardHitbox.intersect(operatorSlot.getHitbox()) && operatorSlot.isEmpty() ||
            cardHitbox.intersect(numberSlot.getHitbox()) && numberSlot.isEmpty() ||
            cardHitbox.intersect(playerActionSlot.getHitbox()) && playerActionSlot.isEmpty()
        ) {
            for (Listener l : getListeners()) {
                l.onCardPlayed(card.getText().toString());
            }
        } else {
            card.resetLocation();
        }
    }

    @Override
    public void addCardToHand(DescribableCard card) {
    }

}

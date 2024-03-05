package com.garrell.co.alchemytcg.screens.game;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.garrell.co.alchemytcg.board.CardSlotView;
import com.garrell.co.alchemytcg.card.DescribableCard;
import com.garrell.co.alchemytcg.card.view.CardHandLayoutManager;
import com.garrell.co.alchemytcg.card.view.DragableCardView;
import com.garrell.co.alchemytcg.card.view.PlayableCardView;
import com.garrell.co.baseapp.R;
import com.garrell.co.baseapp.screens.common.mvcviews.BaseObservableViewMvc;

import java.util.HashMap;
import java.util.Map;

public class GameViewMvcImpl extends BaseObservableViewMvc<GameViewMvc.Listener>
        implements GameViewMvc, DragableCardView.Listener {

    private Map<Integer, Integer> viewIdToCardId = new HashMap<>();
    private final CardHandLayoutManager cardLayoutManager;
    private final CardSlotView operatorSlot;
    private final CardSlotView numberSlot;
    private final CardSlotView playerActionSlot;
    private final TextView baseValue;

    @SuppressLint("ClickableViewAccessibility")
    public GameViewMvcImpl(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_game, container, false));

        ConstraintLayout layout = getRootView().findViewById(R.id.game_layout);
        Space cardHandBaseline = getRootView().findViewById(R.id.card_hand_bottom);
        baseValue = getRootView().findViewById(R.id.base_value);

        cardLayoutManager = new CardHandLayoutManager(layoutInflater.getContext(), layout, cardHandBaseline);

        operatorSlot = getRootView().findViewById(R.id.operator_slot);
        numberSlot = getRootView().findViewById(R.id.number_slot);
        playerActionSlot = getRootView().findViewById(R.id.player_action_slot);
    }

    @Override
    public void addCardToHand(DescribableCard card) {
        DragableCardView view = cardLayoutManager.add(card);
        view.registerListener(this);
        viewIdToCardId.put(view.getId(), card.getId());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setBaseValue(int baseValue) {
        this.baseValue.setText(Integer.toString(baseValue));
    }

    @Override
    public void onCardPlayed(PlayableCardView card, Rect cardHitbox) {
        if (cardHitbox.intersect(operatorSlot.getHitbox())) {
            operatorSlot.setCardInSlot(card);
        }

        if (cardHitbox.intersect(numberSlot.getHitbox())) {
            numberSlot.setCardInSlot(card);
        }

        if (cardHitbox.intersect(playerActionSlot.getHitbox())) {
            playerActionSlot.setCardInSlot(card);
        }

        if (cardHitbox.intersect(operatorSlot.getHitbox()) && operatorSlot.isEmpty() ||
            cardHitbox.intersect(numberSlot.getHitbox()) && numberSlot.isEmpty() ||
            cardHitbox.intersect(playerActionSlot.getHitbox()) && playerActionSlot.isEmpty()
        ) {
            for (Listener l : getListeners()) {
                l.onCardPlayed(card.getDescription());
            }
        } else {
            card.resetLocation();
        }

        // check for all slots filled
        if (allSlotsFilled()) {
            int operatorCardId = getCardIdInSlot(operatorSlot);
            int numberSlotCardId = getCardIdInSlot(numberSlot);
            int playerActionSlotCardId = getCardIdInSlot(playerActionSlot);

            for (Listener l : getListeners()) {
                l.onCardSetPlayed(operatorCardId, numberSlotCardId, playerActionSlotCardId);
            }

            operatorSlot.getCardInSlot().unregisterListener(this);
            numberSlot.getCardInSlot().unregisterListener(this);
            playerActionSlot.getCardInSlot().unregisterListener(this);

            operatorSlot.empty();
            numberSlot.empty();
            playerActionSlot.empty();
        }
    }

    private int getCardIdInSlot(CardSlotView slotView) {
        return viewIdToCardId.get(operatorSlot.getCardInSlot().getViewId());
    }

    private boolean allSlotsFilled() {
        return !operatorSlot.isEmpty() && !numberSlot.isEmpty() && !playerActionSlot.isEmpty();
    }

}

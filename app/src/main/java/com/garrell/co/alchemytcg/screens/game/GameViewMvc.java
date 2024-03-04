package com.garrell.co.alchemytcg.screens.game;

import com.garrell.co.alchemytcg.card.ActionCard;
import com.garrell.co.alchemytcg.card.DescribableCard;
import com.garrell.co.alchemytcg.card.NumberCard;
import com.garrell.co.alchemytcg.card.OperatorCard;
import com.garrell.co.baseapp.screens.common.mvcviews.ObservableViewMvc;

public interface GameViewMvc extends ObservableViewMvc<GameViewMvc.Listener> {

    interface Listener {
        void onCardPlayed(String card);
        void onCardSetPlayed(NumberCard numberCard, OperatorCard operatorCard, ActionCard actionCard);
    }

    void addCardToHand(DescribableCard card);

}

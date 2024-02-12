package com.garrell.co.alchemytcg.screens.game;

import com.garrell.co.baseapp.screens.common.mvcviews.ObservableViewMvc;

public interface GameViewMvc extends ObservableViewMvc<GameViewMvc.Listener> {

    interface Listener {
        void onCardPlayed(String card);
    }

    void addCardToHand(String cardName);

}

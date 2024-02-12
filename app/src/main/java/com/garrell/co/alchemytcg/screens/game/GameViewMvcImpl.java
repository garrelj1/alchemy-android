package com.garrell.co.alchemytcg.screens.game;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.garrell.co.baseapp.R;
import com.garrell.co.baseapp.screens.common.mvcviews.BaseObservableViewMvc;

public class GameViewMvcImpl extends BaseObservableViewMvc<GameViewMvc.Listener> implements GameViewMvc {

    private final Button card;

    public GameViewMvcImpl(LayoutInflater layoutInflater, ViewGroup container) {
        setRootView(layoutInflater.inflate(R.layout.layout_game, container, false));
        card = getRootView().findViewById(R.id.card);
        card.setOnClickListener(v -> {
            for (Listener l : getListeners()) {
                l.onCardPlayed((String) card.getText());
            }
        });
    }

    @Override
    public void addCardToHand(String cardName) {
        card.setText(cardName);
    }

}

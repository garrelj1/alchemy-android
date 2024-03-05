package com.garrell.co.alchemytcg.card.view;

import android.graphics.Rect;

import com.garrell.co.baseapp.common.observable.Observable;

public interface PlayableCardView extends Observable<PlayableCardView.Listener> {

    interface Listener {
        void onCardPlayed(PlayableCardView card, Rect hitbox);
    }

    void resetLocation();
    String getDescription();
    int getViewId();
}

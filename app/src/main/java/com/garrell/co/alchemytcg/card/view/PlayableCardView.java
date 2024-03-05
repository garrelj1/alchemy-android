package com.garrell.co.alchemytcg.card.view;

import android.graphics.Rect;
import android.view.View;

import com.garrell.co.baseapp.common.observable.Observable;

public interface PlayableCardView extends Observable<PlayableCardView.Listener> {

    interface Listener {
        void onCardPlayed(PlayableCardView card, Rect hitbox);
    }

    View getRootView();

    void resetLocation();
    String getDescription();
    int getViewId();
}

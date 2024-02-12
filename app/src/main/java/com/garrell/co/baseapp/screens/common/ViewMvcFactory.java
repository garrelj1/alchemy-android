package com.garrell.co.baseapp.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.garrell.co.alchemytcg.screens.game.GameFragment;
import com.garrell.co.alchemytcg.screens.game.GameViewMvc;
import com.garrell.co.alchemytcg.screens.game.GameViewMvcImpl;
import com.garrell.co.baseapp.screens.common.dialogs.info.InfoViewMvc;
import com.garrell.co.baseapp.screens.common.dialogs.info.InfoViewMvcImpl;
import com.garrell.co.baseapp.screens.common.dialogs.prompt.PromptViewMvc;
import com.garrell.co.baseapp.screens.common.dialogs.prompt.PromptViewMvcImpl;
import com.garrell.co.baseapp.screens.home.HomeViewMvc;
import com.garrell.co.baseapp.screens.home.HomeViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater layoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public InfoViewMvc newInfoViewMvc() {
        return new InfoViewMvcImpl(layoutInflater, null);
    }

    public PromptViewMvc newPromptViewMvc() {
        return new PromptViewMvcImpl(layoutInflater, null);
    }

    public HomeViewMvc newHomeViewMvc(ViewGroup container) {
        return new HomeViewMvcImpl(layoutInflater, container);
    }

    public GameViewMvc newGameViewMvc(ViewGroup container) {
        return new GameViewMvcImpl(layoutInflater, container);
    }

}

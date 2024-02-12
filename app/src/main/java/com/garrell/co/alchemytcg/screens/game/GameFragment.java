package com.garrell.co.alchemytcg.screens.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.garrell.co.baseapp.screens.common.ViewMvcFactory;
import com.garrell.co.baseapp.screens.common.controller.BaseFragment;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GameFragment extends BaseFragment implements GameViewMvc.Listener {

    private ViewMvcFactory viewFactory;
    private GameViewMvc gameViewMvc;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewFactory = getControllerCompositionRoot().getViewMvcFactory();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameViewMvc = viewFactory.newGameViewMvc(container);
        return gameViewMvc.getRootView();
    }

    @Override
    public void onResume() {
        gameViewMvc.addCardToHand(getNewCard());
        gameViewMvc.registerListener(this);

        super.onResume();
    }

    @Override
    public void onCardPlayed(String card) {
        Toast.makeText(getActivity(), "You played: " + card, Toast.LENGTH_LONG).show();

        gameViewMvc.addCardToHand(getNewCard());
    }

    private String getNewCard() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

}

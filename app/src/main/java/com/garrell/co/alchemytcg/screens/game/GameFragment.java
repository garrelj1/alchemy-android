package com.garrell.co.alchemytcg.screens.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.garrell.co.alchemytcg.card.ActionCard;
import com.garrell.co.alchemytcg.card.DescribableCard;
import com.garrell.co.alchemytcg.card.NumberCard;
import com.garrell.co.alchemytcg.card.OperatorCard;
import com.garrell.co.baseapp.screens.common.ViewMvcFactory;
import com.garrell.co.baseapp.screens.common.controller.BaseFragment;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameFragment extends BaseFragment implements GameViewMvc.Listener {

    private static final int BASE_VALUE = 1;
    private ViewMvcFactory viewFactory;
    private GameViewMvc gameViewMvc;

    private PlayCardSetUseCase playCardSetUseCase;
    private Map<Integer, DescribableCard> cards = new HashMap<>();

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewFactory = getControllerCompositionRoot().getViewMvcFactory();

        cards.put(0, new ActionCard(0, ActionCard.TargetPlayer.OPPONENT, ActionCard.TargetAction.HEALTH));
        cards.put(1, new NumberCard(1, 2));
        cards.put(2, new OperatorCard(2, OperatorCard.Operator.ADD));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameViewMvc = viewFactory.newGameViewMvc(container);
        gameViewMvc.setBaseValue(BASE_VALUE);
        for (DescribableCard card : cards.values()) {
            gameViewMvc.addCardToHand(card);
        }
        return gameViewMvc.getRootView();
    }

    @Override
    public void onResume() {
        gameViewMvc.registerListener(this);
        super.onResume();
    }

    @Override
    public void onCardPlayed(String card) {
        Toast.makeText(getActivity(), "You played: " + card, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCardSetPlayed(int numberCardId, int operatorCardId, int actionCardId) {
        String toast = "You played card set: " +
                        cards.get(actionCardId).describe() + " - " +
                        BASE_VALUE + " " +
                        cards.get(numberCardId).describe() + " " +
                        cards.get(operatorCardId).describe() + " ";
        Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
    }

}

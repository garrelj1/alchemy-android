package com.garrell.co.baseapp.screens.common.screennavigator;

import android.app.Activity;
import android.os.Bundle;

import com.garrell.co.alchemytcg.screens.game.GameFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.garrell.co.baseapp.screens.home.HomeFragment;

import java.util.Collections;

import timber.log.Timber;

public class ScreensNavigator {

    private final Activity activity;
    private final FragNavController fragNavController;

    public ScreensNavigator(Activity activity,
                            FragNavController fragNavController) {
        this.activity = activity;
        this.fragNavController = fragNavController;
    }

    public void init(Bundle savedInstanceState) {
        fragNavController.setRootFragments(Collections.singletonList(GameFragment.newInstance()));
        fragNavController.setFragNavLogger((s, throwable) -> Timber.e(throwable, "ScreensNavigator: %s", s));
        fragNavController.setDefaultTransactionOptions(new FragNavTransactionOptions.Builder().build());
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);
    }

    public void onSaveInstanceState(Bundle saveInstanceState) {
        fragNavController.onSaveInstanceState(saveInstanceState);
    }

    public boolean navigateBack() {
        if(fragNavController.isRootFragment()) {
            return false;
        } else {
            fragNavController.popFragment();
            return true;
        }
    }

}

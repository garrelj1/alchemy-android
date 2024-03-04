package com.garrell.co.baseapp.screens.common.views.res;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ResourcesFacade {

    private final Resources resources;

    public ResourcesFacade(Context context) {
        resources = context.getResources();
    }

    public DisplayMetrics getDisplayMetrics() {
        return resources.getDisplayMetrics();
    }

}

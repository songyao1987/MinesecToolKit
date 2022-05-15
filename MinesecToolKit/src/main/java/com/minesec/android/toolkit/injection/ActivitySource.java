package com.minesec.android.toolkit.injection;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

final class ActivitySource implements Source {

    private final Activity activity;

    ActivitySource(Object activity) {
        this((Activity)activity);
    }

    private ActivitySource(Activity activity) {
        this.activity = activity;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return activity.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        return activity.getIntent();
    }
}
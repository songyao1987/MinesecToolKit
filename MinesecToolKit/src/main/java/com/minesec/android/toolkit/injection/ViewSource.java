package com.minesec.android.toolkit.injection;

import android.content.Intent;
import android.view.View;

final class ViewSource implements Source {

    private final View view;

    ViewSource(Object view) {
        this((View)view);
    }

    private ViewSource(View view) {
        this.view = view;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        return new Intent();
    }
}


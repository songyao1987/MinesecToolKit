package com.minesec.android.toolkit.injection;

import android.content.Intent;
import android.view.View;

interface Source {

    <T extends View> T findViewById(int id);

    Intent getIntent();

}
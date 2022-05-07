package com.minesec.android.toolkit.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class DefaultObjectStore<T> extends BaseStore<T> implements ObjectStore<T> {

    public DefaultObjectStore(Context context, Class<T> modelType) {
        super(context, modelType);
    }

    public DefaultObjectStore(Context context, String name, Class<T> modelType) {
        super(context, name, modelType);
    }

    public DefaultObjectStore(SharedPreferences sp, Class<T> modelType) {
        super(sp, modelType);
    }

}
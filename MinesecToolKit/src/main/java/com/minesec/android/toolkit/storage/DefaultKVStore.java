package com.minesec.android.toolkit.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class DefaultKVStore<T> extends BaseStore<T> implements KVStore<T> {

    public DefaultKVStore(Context context, Class<T> modelType) {
        super(context, modelType);
    }

    public DefaultKVStore(Context context, String name, Class<T> modelType) {
        super(context, name, modelType);
    }

    public DefaultKVStore(SharedPreferences sp, Class<T> modelType) {
        super(sp, modelType);
    }

    @Override
    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    @Override
    public String getString(String key, String def) {
        return sp.getString(key, def);
    }

}

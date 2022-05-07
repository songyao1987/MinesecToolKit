package com.minesec.android.toolkit.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.minesec.android.toolkit.text.Json;

public class JsonObjectStore<T> extends BaseStore<T> implements ObjectStore<T> {

    private static final String PAYLOAD = "payload";

    public JsonObjectStore(Context context, Class<T> modelType) {
        super(context, modelType);
    }

    public JsonObjectStore(Context context, String name, Class<T> modelType) {
        super(context, name, modelType);
    }

    public JsonObjectStore(SharedPreferences sp, Class<T> modelType) {
        super(sp, modelType);
    }

    @Override
    public boolean exist() {
        return sp.contains(PAYLOAD);
    }

    @Override
    public void save(T model) {
        String payload = Json.getEncoder().encode(model);
        sp.edit().putString(PAYLOAD, payload).apply();
    }

    @Override
    public T load() {
        String payload = sp.getString(PAYLOAD, null);
        if (payload == null) {
            return null;
        }
        return Json.getDecoder().decode(payload, modelType);
    }

    @Override
    public void clear() {
        sp.edit().clear().apply();
    }

}
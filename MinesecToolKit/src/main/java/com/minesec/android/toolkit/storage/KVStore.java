package com.minesec.android.toolkit.storage;

public interface KVStore<T> extends Store<T> {

    void putString(String key, String value);

    String getString(String key, String def);

    default String getString(String key) {
        return getString(key, null);
    }

}
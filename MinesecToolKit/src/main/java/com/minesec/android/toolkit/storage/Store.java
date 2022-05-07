package com.minesec.android.toolkit.storage;

public interface Store<T> {

    boolean exist();

    void save(T model);

    T load();

    void clear();

}
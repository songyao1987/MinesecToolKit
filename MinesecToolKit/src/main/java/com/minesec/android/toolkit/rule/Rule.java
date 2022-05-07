package com.minesec.android.toolkit.rule;

public interface Rule<T> {

    boolean accept(T value);

}
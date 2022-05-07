package com.minesec.android.toolkit.util;

public abstract class Assertion {
    public static void notNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    public static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " is null");
        }
    }

    public static void hasLength(String str, String name) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(name + " is empty");
        }
    }
}

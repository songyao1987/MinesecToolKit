package com.minesec.android.toolkit.injection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

abstract class AbstractBinding implements Binding {

    static Class<?> getParameterizedType(Field field) {
        return (Class<?>)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
    }

}

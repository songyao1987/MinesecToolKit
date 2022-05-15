package com.minesec.android.toolkit.injection;

import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

class ViewBinding extends AbstractBinding {

    private static final Logger logger = LoggerFactory.getLogger("ViewBinding");

    @Override
    public void bind(Object target, Source source) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            bind(target, field, source);
        }
    }

    private void bind(Object target, Field field, Source source) {
        if (!field.isAnnotationPresent(BindView.class)) {
            return;
        }
        BindView bindView = field.getAnnotation(BindView.class);
        int viewId = bindView.value();
        if (viewId == View.NO_ID) {
            throw new IllegalArgumentException("default view id is not allow");
        }
        View view = source.findViewById(viewId);
        if (view == null) {
            return;
        }
        field.setAccessible(true);
        try {
            field.set(target, view);
        } catch (Exception e) {
            logger.error("BindView failed, name={}", field.getName(), e);
        }
    }

}

package com.minesec.android.toolkit.injection;

import android.content.Intent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;

class DataBinding extends AbstractBinding {

    private static final Logger logger = LoggerFactory.getLogger("DataBinding");

    private static final Class<?> ObservableFieldCls = getObservableFieldCls();

    private static Class<?> getObservableFieldCls() {
        try {
            return Class.forName("android.databinding.ObservableField");
        } catch (ClassNotFoundException e) {
            logger.info("android.databinding.ObservableField not found");
            return null;
        }
    }

    @Override
    public void bind(Object target, Source source) {
        Intent intent = source.getIntent();
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            bind(target, field, intent);
        }
    }

    private void bind(Object target, Field field, Intent intent) {
        if (!field.isAnnotationPresent(Autowired.class)) {
            return;
        }
        Autowired autowired = field.getAnnotation(Autowired.class);
        String name = autowired.name();
        if (!intent.hasExtra(name)) {
            logger.warn("Autowired missing extra key={}", name);
            return;
        }
        field.setAccessible(true);
        try {
            if (boolean.class.equals(field.getType())) {
                boolean value = intent.getBooleanExtra(name, false);
                field.setBoolean(target, value);
            } else if (String.class.equals(field.getType())) {
                String value = intent.getStringExtra(name);
                field.set(target, value);
            } else if (int.class.equals(field.getType())) {
                int value = intent.getIntExtra(name, 0);
                field.setInt(target, value);
            } else if (double.class.equals(field.getType())) {
                double value = intent.getDoubleExtra(name, 0D);
                field.setDouble(target, value);
            } else if (BigDecimal.class.equals(field.getType())) {
                BigDecimal value = new BigDecimal(intent.getStringExtra(name));
                field.set(target, value);
            } else if (ObservableFieldCls != null && field.getType().isAssignableFrom(ObservableFieldCls)) {
                Class<?> fieldParameterizedType = getParameterizedType(field);
                if (String.class.equals(fieldParameterizedType)) {
                    ObservableFieldCls.getMethod("set", Object.class)
                            .invoke(field.get(target), intent.getStringExtra(name));
                } else {
                    throw new IllegalArgumentException("ObservableField<" + fieldParameterizedType.getSimpleName() + ">");
                }
            } else {
                throw new IllegalArgumentException("field type: " + field.getType().getName());
            }
        } catch (Exception e) {
            logger.error("Autowired inject failed, field={}, name={}", field.getName(), name, e);
        }
    }

}

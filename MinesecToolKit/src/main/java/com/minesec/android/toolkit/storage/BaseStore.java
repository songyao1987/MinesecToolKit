package com.minesec.android.toolkit.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

public abstract class BaseStore<T> implements Store<T> {

    private static final Logger logger = LoggerFactory.getLogger("BaseStore");

    private static final String IS_PERSISTENCE_KEY = "_persistence_";

    final SharedPreferences sp;
    final Class<T> modelType;

    public BaseStore(Context context, Class<T> modelType) {
        this(PreferenceManager.getDefaultSharedPreferences(context), modelType);
    }

    public BaseStore(Context context, String name, Class<T> modelType) {
        this(context.getSharedPreferences(name, Context.MODE_PRIVATE), modelType);
    }

    public BaseStore(SharedPreferences sp, Class<T> modelType) {
        this.sp = sp;
        this.modelType = modelType;
    }

    @Override
    public boolean exist() {
        return sp.getBoolean(IS_PERSISTENCE_KEY, false);
    }

    @Override
    public void save(T model) {
        try {
            SharedPreferences.Editor editor = sp.edit();
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (ignored(field)) {
                    continue;
                }
                field.setAccessible(true);
                saveField(editor, model, field);
            }
            editor.putBoolean(IS_PERSISTENCE_KEY, true).apply();
        } catch (Exception e) {
            logger.error("{}.save() failed", getClass().getSimpleName(), e);
        }
    }

    @Override
    public T load() {
        if (!exist()) {
            return null;
        }
        try {
            Constructor<T> constructor = modelType.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();

            Field[] fields = modelType.getDeclaredFields();
            for (Field field : fields) {
                if (ignored(field)) {
                    continue;
                }
                field.setAccessible(true);
                loadValue(this.sp, instance, field);
            }

            return instance;
        } catch (Exception e) {
            logger.error("{}.load() failed", getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public void clear() {
        sp.edit().clear().apply();
    }

    private static boolean ignored(Field field) {
        return field.isAnnotationPresent(Ignore.class)
                || Modifier.isStatic(field.getModifiers())
                || Modifier.isTransient(field.getModifiers());
    }

    private static String getKey(Field field) {
        if (field.isAnnotationPresent(Key.class)) {
            Key annotation = field.getAnnotation(Key.class);
            return annotation.name();
        }
        return field.getName();
    }

    private static String getDefault(Field field, String def) {
        if (field.isAnnotationPresent(Default.class)) {
            Default annotation = field.getAnnotation(Default.class);
            return annotation.value();
        }
        return def;
    }

    private static void saveField(SharedPreferences.Editor editor, Object obj, Field field) throws IllegalAccessException {
        Class<?> type = field.getType();
        String key = getKey(field);
        if (String.class.equals(type)) {
            String val = (String)field.get(obj);
            if (val != null) {
                editor.putString(key, val);
            }
            return;
        }
        if (BigDecimal.class.equals(type)) {
            BigDecimal val = (BigDecimal)field.get(obj);
            if (val != null) {
                editor.putString(key, val.toPlainString());
            }
            return;
        }
        if (boolean.class.equals(type)) {
            editor.putBoolean(key, field.getBoolean(obj));
            return;
        }
        if (Boolean.class.equals(type)) {
            Object val = field.get(obj);
            if (val != null) {
                editor.putBoolean(key, (boolean)val);
            }
            return;
        }
        throw new IllegalAccessException("save error: type=" + type.getName() + ", name=" + field.getName());
    }

    private static void loadValue(SharedPreferences sp, Object obj, Field field) throws IllegalAccessException {
        Class<?> type = field.getType();
        String key = getKey(field);
        String strDefault = getDefault(field, null);

        if (String.class.equals(type)) {
            field.set(obj, sp.getString(key, strDefault));
            return;
        }

        if (BigDecimal.class.equals(type)) {
            String value = sp.getString(key, strDefault);
            if (value != null && value.length() > 0) {
                field.set(obj, new BigDecimal(value));
            }
            return;
        }

        if (boolean.class.equals(type) || Boolean.class.equals(type)) {
            boolean def = false;
            if (strDefault != null) {
                def = Boolean.parseBoolean(strDefault);
            }
            if (sp.contains(key)) {
                field.set(obj, sp.getBoolean(key, def));
            }
            return;
        }

        throw new IllegalAccessException("load error: type=" + type.getName() + ", name=" + field.getName());
    }

}

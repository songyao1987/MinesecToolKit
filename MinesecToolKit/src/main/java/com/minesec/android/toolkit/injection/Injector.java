package com.minesec.android.toolkit.injection;

import android.app.Activity;
import android.view.View;

import com.minesec.android.toolkit.util.Assertion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Injector {

    private static final Logger logger = LoggerFactory.getLogger("Injector");

    public static boolean BINDING_DATA = true;
    public static boolean BINDING_VIEW = true;
    public static boolean BINDING_EVENT = true;

    private static final Class<?> ObservableFieldCls = getObservableFieldCls();

    private static Class<?> getObservableFieldCls() {
        try {
            return Class.forName("android.databinding.ObservableField");
        } catch (ClassNotFoundException e) {
            logger.info("android.databinding.ObservableField not found");
            return null;
        }
    }

    public static void inject(Object target) {
        inject(target, target);
    }

    public static void inject(Object target, Object source) {
        Assertion.notNull(target, "target");
        if (source instanceof Activity) {
            inject(target, new ActivitySource(source));
            return;
        }
        if (source instanceof View) {
            inject(target, new ViewSource(source));
            return;
        }
        throw new IllegalArgumentException("source type: " + source.getClass().getName());
    }

    private static void inject(Object target, Source source) {
        if (BINDING_DATA) {
            new DataBinding().bind(target, source);
        }
        if (BINDING_VIEW) {
            new ViewBinding().bind(target, source);
        }
        if (BINDING_EVENT) {
            new EventBinding().bind(target, source);
        }
    }

}
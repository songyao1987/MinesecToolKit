package com.minesec.android.toolkit.router;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.minesec.android.toolkit.apk.ApkException;
import com.minesec.android.toolkit.apk.Scanner;
import com.minesec.android.toolkit.rule.Rule;
import com.minesec.android.toolkit.util.Assertion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Router {

    private static final Logger logger = LoggerFactory.getLogger("Router");

    private static final class Holder {
        private static volatile Application app;
        private static final Map<String, Class<?>> table = new HashMap<>();
    }

    public static void init(Application application, Rule<String> rule) {
        Assertion.notNull(rule, "rule");
        Holder.app = application;
        try {
            Set<String> classes = Scanner.getAllClasses(application);
            for (String className : classes) {
                if (!rule.accept(className)) {
                    continue;
                }
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Route.class)) {
                        Route route = clazz.getAnnotation(Route.class);
                        assert route != null;
                        register(route.path(), clazz);
                    }
                } catch (ClassNotFoundException e) {
                    logger.warn("class({}) not found", className);
                }
            }
        } catch (ApkException e) {
            logger.error("load router table error: {}", e.getMessage(), e);
        }
        logger.info("init route table size={}", Holder.table.size());
    }

    public static void register(Class<?> type) {
        if (!type.isAnnotationPresent(Route.class)) {
            throw new IllegalArgumentException("Route error: " + type.getName());
        }
        Route route = type.getAnnotation(Route.class);
        assert route != null;
        register(route.path(), type);
    }

    private static void register(String path, Class<?> target) {
        Assertion.hasLength(path, "route path of " + target.getName());
        logger.debug("register route: {} => {}", path, target.getName());
        Holder.table.put(path, target);
    }

    public static Router NEW() {
        Assertion.notNull(Holder.app, "application");
        return new Router(Holder.app);
    }

    private final Application application;

    private Class<?> target;
    private final Bundle bundle = new Bundle();
    private int flags = 0;

    private Router(Application application) {
        this.application = application;
    }

    /**
     * navigation by route path
     *
     * @param path path pre registered to route table
     */
    public Router path(String path) {
        if (!Holder.table.containsKey(path)) {
            throw new IllegalArgumentException("no route: " + path);
        }
        this.target = Holder.table.get(path);
        return this;
    }

    /**
     * navigation by specified activity class
     *
     * @param target next activity class
     */
    public Router target(Class<?> target) {
        this.target = target;
        return this;
    }

    public Router addFlags(int...flags) {
        for (int flag : flags) {
            this.flags |= flag;
        }
        return this;
    }

    public Router withDouble(String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }

    public Router withBoolean(String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public Router withString(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public Router withInt(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public Router withBigDecimal(String key, BigDecimal value) {
        if (value != null) {
            bundle.putString(key, value.toPlainString());
        }
        return this;
    }

    public Router withStringList(String key, ArrayList<String> value) {
        if (value != null) {
            bundle.putStringArrayList(key, value);
        }
        return this;
    }

    public Router withStringArray(String key, String[] value) {
        if (value != null) {
            bundle.putStringArray(key, value);
        }
        return this;
    }

    public void navigation() {
        navigation(application);
    }

    public void navigation(Context context) {
        Intent intent = new Intent(context, target);
        intent.putExtras(bundle);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(flags);
        context.startActivity(intent);
    }

}

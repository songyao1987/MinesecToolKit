package com.minesec.android.toolkit.injection;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

class EventBinding extends AbstractBinding {

    private static final Logger logger = LoggerFactory.getLogger("EventBinding");

    @Override
    public void bind(Object target, Source source) {
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            bindClick(target, method, source);
            bindTextChanged(target, method, source);
            bindItemClick(target, method, source);
        }
    }

    private void bindItemClick(Object target, Method method, Source source) {
        if (!method.isAnnotationPresent(OnItemClick.class)) {
            return;
        }
        OnItemClick itemClick = method.getAnnotation(OnItemClick.class);
        for (int id : itemClick.value()) {
            if (id == View.NO_ID) {
                return;
            }
            View view = source.findViewById(id);
            if (view == null) {
                return;
            }
            if (!(view instanceof AdapterView)) {
                throw new IllegalArgumentException("only support AdapterView");
            }
            method.setAccessible(true);
            bindItemClick(target, method, (AdapterView<?>) view);
        }
    }

    private void bindItemClick(Object target, Method method, AdapterView<?> parent) {
        Class<?>[] types = method.getParameterTypes();
        if (types.length == 3
                && types[0].isAssignableFrom(AdapterView.class)
                && types[1].isAssignableFrom(View.class)
                && int.class.equals(types[2])) {
            parent.setOnItemClickListener((parent1, view, position, id) -> invoke(target, method, parent1, view, position));
            return;
        }
        throw new IllegalArgumentException("OnItemClick " + target.getClass().getSimpleName() + "."
                + method.getName() + ": parameter count=" + types.length);
    }

    private void bindTextChanged(Object target, Method method, Source source) {
        if (!method.isAnnotationPresent(OnTextChanged.class)) {
            return;
        }
        OnTextChanged textChanged = method.getAnnotation(OnTextChanged.class);
        for (int id : textChanged.value()) {
            if (id == View.NO_ID) {
                return;
            }
            View view = source.findViewById(id);
            if (view == null) {
                return;
            }
            if (!(view instanceof TextView)) {
                throw new IllegalArgumentException("only support TextView");
            }
            method.setAccessible(true);
            bindTextChanged(target, method, (TextView) view);
        }
    }

    private void bindTextChanged(Object target, Method method, TextView view) {
        Class<?>[] types = method.getParameterTypes();
        if (types.length == 1 && types[0].isAssignableFrom(String.class)) {
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    invoke(target, method, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return;
        }
        throw new IllegalArgumentException("OnTextChanged " + target.getClass().getSimpleName() + "."
                + method.getName() + ": parameter count=" + types.length);
    }

    private void bindClick(Object target, Method method, Source source) {
        if (!method.isAnnotationPresent(OnClick.class)) {
            return;
        }
        OnClick onClick = method.getAnnotation(OnClick.class);
        for (int id : onClick.value()) {
            if (id == View.NO_ID) {
                return;
            }
            View view = source.findViewById(id);
            if (view == null) {
                return;
            }
            method.setAccessible(true);
            bindClick(target, method, view);
        }
    }

    private void bindClick(Object target, Method method, View view) {
        Class<?>[] types = method.getParameterTypes();
        if (types.length == 0) {
            view.setOnClickListener(v -> invoke(target, method));
            return;
        }
        if (types.length == 1 && types[0].isAssignableFrom(View.class)) {
            view.setOnClickListener(v -> invoke(target, method, view));
            return;
        }
        throw new IllegalArgumentException("OnClick " + target.getClass().getSimpleName() + "."
                + method.getName() + ": parameter count=" + types.length);
    }

    private void invoke(Object target, Method method) {
        try {
            method.invoke(target);
        } catch (Exception e) {
            logger.error("error invoke {}.{}()", target.getClass().getSimpleName(), method.getName(), e);
        }
    }

    private void invoke(Object target, Method method, Object...args) {
        try {
            method.invoke(target, args);
        } catch (Exception e) {
            logger.error("error invoke {}.{}({})",
                    target.getClass().getSimpleName(), method.getName(), args.length, e);
        }
    }

}

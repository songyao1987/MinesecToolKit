package com.minesec.android.toolkit;

import android.app.Application;
import android.content.Context;

import com.minesec.android.toolkit.util.Assertion;

public abstract class AppHolder {

    public static void init(Application application) {
        Holder.INSTANCE = application;
    }

    public static Application app() {
        Assertion.notNull(Holder.INSTANCE, "application");
        return Holder.INSTANCE;
    }

    public static Context context() {
        Assertion.notNull(Holder.INSTANCE, "application");
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static volatile Application INSTANCE;
    }

}

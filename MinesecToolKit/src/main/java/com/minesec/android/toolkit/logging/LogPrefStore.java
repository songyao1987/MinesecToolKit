package com.minesec.android.toolkit.logging;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author eric.song
 * @since 2022/9/13 9:58
 */
public final class LogPrefStore implements LogPreference {

    private final SharedPreferences sp;

    public LogPrefStore(Context ctx) {
        this(ctx.getSharedPreferences(PREF_SP_NAME, Context.MODE_PRIVATE));
    }

    private LogPrefStore(SharedPreferences sp) {
        this.sp = sp;
    }

    public void initialize() {
        if (sp.contains(PREF_INITIALIZED)) {
            applyConfig();
        } else {
            saveDefaults();
        }
    }

    private void applyConfig() {
        LogController.apply(getConfig());
    }

    public LogConfig getConfig() {
        LogConfig config = new LogConfig();
        config.setLogEnabled(logEnabled());
        config.setLogLevel(logLevel());
        config.setLogTcpEnabled(logTcpEnabled());
        config.setLogHttpEnabled(logHttpEnabled());
        return config;
    }

    private boolean logEnabled() {
        return sp.getBoolean(PREF_LOG_ENABLED, LogOpts.ENABLE_LOGGING);
    }

    private String logLevel() {
        return sp.getString(PREF_LOG_LEVEL, LogOpts.DEFAULT_LEVEL);
    }

    private boolean logTcpEnabled() {
        return sp.getBoolean(PREF_LOG_TCP_ENABLED, LogOpts.ENABLE_TCP_LOGGING);
    }

    private boolean logHttpEnabled() {
        return sp.getBoolean(PREF_LOG_HTTP_ENABLED, LogOpts.ENABLE_HTTP_LOGGING);
    }

    private void saveDefaults() {
        sp.edit()
                .putBoolean(PREF_LOG_ENABLED, LogOpts.ENABLE_LOGGING)
                .putString(PREF_LOG_LEVEL, LogOpts.DEFAULT_LEVEL)
                .putBoolean(PREF_LOG_TCP_ENABLED, LogOpts.ENABLE_TCP_LOGGING)
                .putBoolean(PREF_LOG_HTTP_ENABLED, LogOpts.ENABLE_HTTP_LOGGING)
                .putBoolean(PREF_INITIALIZED, true)
                .apply();
    }

}

package com.minesec.android.toolkit.logging;


import ch.qos.logback.classic.Level;

/**
 * @author eric.song
 * @since 2022/9/13 9:55
 */
public abstract class LogController implements Naming {

    public static void apply(LogConfig config) {
        if (config.isLogEnabled()) {
            setLogLevel(config.getLogLevel());
            setTcpLogEnabled(config.isLogTcpEnabled());
            setHttpLogEnabled(config.isLogHttpEnabled());
        } else {
            setLogLevel(Level.OFF);
            setTcpLogEnabled(false);
            setHttpLogEnabled(false);
        }
    }

    private static void setLogLevel(String levelStr) {
        setLogLevel(Level.toLevel(levelStr));
    }

    private static void setLogLevel(Level level) {
        Logging.getLogger(ROOT_LOGGER_NAME)
                .setLevel(level);
    }

    private static void setTcpLogEnabled(boolean enabled) {
        Logging.getLogger(TCP_LOGGER_NAME)
                .setLevel(enabled ? Level.ALL : Level.OFF);
    }

    private static void setHttpLogEnabled(boolean enabled) {
        Logging.getLogger(HTTP_LOGGER_NAME)
                .setLevel(enabled ? Level.ALL : Level.OFF);
    }

}

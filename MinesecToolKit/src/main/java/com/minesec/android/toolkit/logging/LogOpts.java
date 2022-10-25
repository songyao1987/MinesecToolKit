package com.minesec.android.toolkit.logging;

import java.util.TimeZone;

/**
 * @author eric.song
 * @since 2022/9/13 10:04
 */
public abstract class LogOpts {

    /**
     * enable/disable logging
     */
    public static boolean ENABLE_LOGGING = true;

    /**
     * set default log level
     */
    public static String DEFAULT_LEVEL = LogLevel.TRACE;

    /**
     * enable/disable tcp logging
     */
    public static boolean ENABLE_TCP_LOGGING = false;

    /**
     * enable/disable http logging
     */
    public static boolean ENABLE_HTTP_LOGGING = true;

    public static String DEFAULT_LOGFILE_NAME = "MSPay";

    public static int DEFAULT_ROLLING_MAX_HISTORY = 2;

    public static String DEFAULT_ROLLING_NAME_PATTERN = "-%d{yyyyMMdd," + TimeZone.getDefault().getID() + "}";

    public static String DEFAULT_LOGFILE_PATH = null;

}

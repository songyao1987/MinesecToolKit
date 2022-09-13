package com.minesec.android.toolkit.logging;

import ch.qos.logback.classic.Level;

/**
 * @author eric.song
 * @since 2022/9/13 9:57
 */
public interface LogLevel {

    String TRACE = Level.TRACE.toString();

    String DEBUG = Level.DEBUG.toString();

    String INFO = Level.INFO.toString();

    String WARN = Level.WARN.toString();

    String ERROR = Level.ERROR.toString();

    String OFF = Level.OFF.toString();

    String[] ENTRIES = {TRACE, DEBUG, INFO, WARN, ERROR, OFF};

}

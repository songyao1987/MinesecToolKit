package com.minesec.android.toolkit.logging;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;

/**
 * @author eric.song
 * @since 2022/9/13 9:56
 */
abstract class Logging {

    static LoggerContext getContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    static Logger getLogger(String name) {
        return getContext().getLogger(name);
    }

    static Filter<ILoggingEvent> getFilter(Level level) {
        ThresholdFilter filter = new ThresholdFilter();
        filter.setContext(getContext());
        //filter.setName("FILTER");
        filter.setLevel(level.toString());
        filter.start();
        return filter;
    }

}

package com.minesec.android.toolkit.logging;

import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.song
 * @since 2022/9/13 9:58
 */
@Slf4j
class LogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler defaultExceptionHandler;

    LogUncaughtExceptionHandler() {
        this.defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("crashed!", e);
        if (defaultExceptionHandler != null) {
            defaultExceptionHandler.uncaughtException(t, e);
        }
    }

}

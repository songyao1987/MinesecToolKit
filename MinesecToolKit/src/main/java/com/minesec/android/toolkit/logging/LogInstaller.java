package com.minesec.android.toolkit.logging;

import android.os.Environment;
import android.text.TextUtils;
import java.util.Date;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eric.song
 * @since 2022/9/13 9:57
 */
@Slf4j
public abstract class LogInstaller implements Naming {

    public static void install() {
        LoggerContext context = Logging.getContext();
        Logger root = context.getLogger(ROOT_LOGGER_NAME);
        root.setLevel(LogOpts.ENABLE_LOGGING ? Level.toLevel(LogOpts.DEFAULT_LEVEL) : Level.OFF);

        root.addAppender(createLogcatAppender(context));

        root.addAppender(createFileAppender(context));

        disableNettyLogging(context);

        initTcpLogging(context);

        initHttpLogging(context);

        Thread.setDefaultUncaughtExceptionHandler(new LogUncaughtExceptionHandler());

        log.info("log module installed");
    }

    private static LogcatAppender createLogcatAppender(LoggerContext context) {
        LogcatAppender appender = new LogcatAppender();
        appender.setContext(context);
        appender.setName(LOGCAT_APPENDER_NAME);

        PatternLayoutEncoder tagEncoder = new PatternLayoutEncoder();
        tagEncoder.setContext(context);
        tagEncoder.setPattern("%logger{0}");
        tagEncoder.start();

        PatternLayoutEncoder msgEncoder = new PatternLayoutEncoder();
        msgEncoder.setContext(context);
        msgEncoder.setPattern("[%thread] %msg");
        msgEncoder.start();

        appender.setTagEncoder(tagEncoder);
        appender.setEncoder(msgEncoder);
        appender.start();

        return appender;
    }

    private static FileAppender<ILoggingEvent> createFileAppender(LoggerContext context) {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(context);
        appender.setName(FILE_APPENDER_NAME);
        appender.setFile(getFile());

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{ISO8601} [%thread] %level %logger{0} - %msg%n");
        encoder.start();

        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(appender);
        rollingPolicy.setFileNamePattern(getRollingFile());
        rollingPolicy.setMaxHistory(LogOpts.DEFAULT_ROLLING_MAX_HISTORY);
        rollingPolicy.start();

        TimeBasedFileNamingAndTriggeringPolicy triggeringPolicy = rollingPolicy.getTimeBasedFileNamingAndTriggeringPolicy();
        if (triggeringPolicy != null && triggeringPolicy.getArchiveRemover() != null) {
            Date now = new Date(triggeringPolicy.getCurrentTime());
            triggeringPolicy.getArchiveRemover().cleanAsynchronously(now);
        }

        appender.setEncoder(encoder);
        appender.setRollingPolicy(rollingPolicy);
        appender.start();

        return appender;
    }

    private static void disableNettyLogging(LoggerContext context) {
        context.getLogger(NETTY_ROOT_NAME)
                .setLevel(Level.OFF);
    }

    private static void initTcpLogging(LoggerContext context) {
        Logger logger = context.getLogger(TCP_LOGGER_NAME);
        logger.setLevel(LogOpts.ENABLE_LOGGING && LogOpts.ENABLE_TCP_LOGGING ? Level.ALL : Level.OFF);
    }

    private static void initHttpLogging(LoggerContext context) {
        Logger logger = context.getLogger(HTTP_LOGGER_NAME);
        logger.setLevel(LogOpts.ENABLE_LOGGING && LogOpts.ENABLE_HTTP_LOGGING ? Level.ALL : Level.OFF);
    }

    private static String getFile() {
        return getLogPath() + "/" + LogOpts.DEFAULT_LOGFILE_NAME + ".log";
    }

    private static String getRollingFile() {
        return getLogPath() + "/" + LogOpts.DEFAULT_LOGFILE_NAME + LogOpts.DEFAULT_ROLLING_NAME_PATTERN + ".log";
    }

    private static String getLogPath() {
        if (TextUtils.isEmpty(LogOpts.DEFAULT_LOGFILE_PATH)) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        } else {
            return LogOpts.DEFAULT_LOGFILE_PATH;
        }
    }

}

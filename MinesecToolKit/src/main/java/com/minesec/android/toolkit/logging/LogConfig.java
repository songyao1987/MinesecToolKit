package com.minesec.android.toolkit.logging;

import lombok.Getter;
import lombok.Setter;

/**
 * @author eric.song
 * @since 2022/9/13 9:55
 */
@Getter
@Setter
public final class LogConfig {

    private boolean logEnabled;
    private String logLevel;
    private boolean logTcpEnabled;
    private boolean logHttpEnabled;

}
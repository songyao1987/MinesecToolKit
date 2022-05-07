package com.minesec.android.toolkit.apk;

public class ApkException extends Exception {
    public ApkException(String message) {
        super(message);
    }

    public ApkException(String message, Throwable cause) {
        super(message, cause);
    }
}

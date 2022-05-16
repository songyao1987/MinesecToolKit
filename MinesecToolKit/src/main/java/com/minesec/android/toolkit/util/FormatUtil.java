package com.minesec.android.toolkit.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
    public static String getFormatTransactionDateTime(String pattern,Date date) {
        if (date == null) {
            return "";
        }
        if (TextUtils.isEmpty(pattern)) {
            return "";
        }
        return new SimpleDateFormat(pattern, Locale.getDefault())
                .format(date);
    }
}

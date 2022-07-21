package com.minesec.android.toolkit.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
    private final static String DATE_TIME_PATTERN_NO_SECOND = "yyyy/MM/dd,HH:mm";
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

    public static String convertMineSecDataTime(String dateTime) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date= simpleDateFormat.parse(dateTime);
            return getFormatTransactionDateTime(DATE_TIME_PATTERN_NO_SECOND, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}

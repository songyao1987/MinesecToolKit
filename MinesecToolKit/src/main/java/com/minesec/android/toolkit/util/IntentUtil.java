package com.minesec.android.toolkit.util;

import android.os.Bundle;
import android.util.Log;

/**
 * @author eric.song
 * @since 2022/8/12 14:55
 */
public class IntentUtil {
    private static final String TAG = "IntentUtil";

    public static String getStringExtra(Bundle bundle, String key) {
        String str = null;
        if (bundle != null) {
            try {
                str = bundle.getString(key);
            } catch (Exception e) {
                Log.e(TAG, "getStringExtra exception:" + e.getMessage());
            }
        }
        return str;
    }

    public static int getIntExtra(Bundle bundle, String key) {
        int value = -1;
        if (bundle != null) {
            try {
                value = bundle.getInt(key, -1);
            } catch (Exception e) {
                Log.e(TAG, "getIntExtra exception:" + e.getMessage());
            }
        }
        return value;
    }

    public static long getLongExtra(Bundle bundle, String key) {
        long value = -1L;
        if (bundle != null) {
            try {
                value = bundle.getLong(key, -1L);
            } catch (Exception e) {
                Log.e(TAG, "getLongExtra exception:" + e.getMessage());
            }
        }
        return value;
    }

    public static boolean getBooleanExtra(Bundle bundle, String key) {
        boolean value = true;
        if (bundle != null) {
            try {
                value = bundle.getBoolean(key, true);
            } catch (Exception e) {
                Log.e(TAG, "getStringExtra exception:" + e.getMessage());
            }
        }
        return value;
    }

    public static byte[] getByteArrayExtra(Bundle bundle, String key) {
        byte[] value = null;
        if (bundle != null) {
            try {
                value = bundle.getByteArray(key);
            } catch (Exception e) {
                Log.e(TAG, "getByteArrayExtra exception:" + e.getMessage());
            }
        }
        return value;
    }
}
package com.minesec.android.toolkit.util;

/**
 * @author eric.song
 * @since 2022/7/28 9:48
 */
public class StringUtils {
    public StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isDigitsOnly(CharSequence str) {
        int len = str.length();

        int cp;
        for(int i = 0; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(str, i);
            if (!Character.isDigit(cp)) {
                return false;
            }
        }

        return true;
    }

    public static int[] toDigits(CharSequence str) {
        int len = str.length();
        int[] digits = new int[len];

        for(int i = 0; i < len; ++i) {
            digits[i] = str.charAt(i) - 48;
        }

        return digits;
    }
}

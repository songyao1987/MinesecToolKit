package com.minesec.android.toolkit.util;

import android.text.TextUtils;

/**
 * @author eric.song
 * @since 2022/7/22 14:29
 */
public class Track2DataUtil {
    private static final String TRACK_2_DATA_SPLIT = "=";

    private static final String TRACK_2_DATA_SPLIT_STR = "D";
    /**
     * Get PAN From Track2Data
     *
     * @param track2Data
     * @return
     */
    public static String getPanFromTrack2Data(String track2Data) {

        if (TextUtils.isEmpty(track2Data)) {
            return null;
        }

        if (track2Data.contains(TRACK_2_DATA_SPLIT)) {
            return track2Data.split(TRACK_2_DATA_SPLIT)[0];
        }

        if (track2Data.contains(TRACK_2_DATA_SPLIT_STR)) {
            return track2Data.split(TRACK_2_DATA_SPLIT_STR)[0];
        }

        return null;
    }

    /**
     * Verify Track2Data
     *
     * @param track2Data
     * @return
     */
    public static boolean VerifyTrack2Data(String track2Data) {

        if (TextUtils.isEmpty(track2Data)) {
            return false;
        }

        if (!track2Data.contains(TRACK_2_DATA_SPLIT) && !track2Data.toUpperCase().contains(TRACK_2_DATA_SPLIT_STR)) {
            return false;
        }

        return true;
    }

    /**
     * Get ExpDate From Track2Data
     *
     * @param track2Data
     * @return
     */
    public static String getExpDataFromTrack2Data(String track2Data) {
        if (TextUtils.isEmpty(track2Data)) {
            return null;
        }

        if (track2Data.contains(TRACK_2_DATA_SPLIT)) {
            return track2Data.split(TRACK_2_DATA_SPLIT)[1].substring(0, 4);
        }

        if (track2Data.contains(TRACK_2_DATA_SPLIT_STR)) {
            return track2Data.split(TRACK_2_DATA_SPLIT_STR)[1].substring(0, 4);
        }

        return null;
    }

    /**
     * Get masked Pan
     * @param pan
     * @return
     */
    public static String getMaskPan(String pan) {
        int i;
        String split = "-";
        String maskChar = "X";

        if (TextUtils.isEmpty(pan) || pan.length() < 12 || pan.length() > 19) {
            return "";
        }
        int maskLen = pan.length() - 8;
        StringBuilder sb = new StringBuilder();
        sb.append(pan.substring(0,4));
        sb.append(split);
        for (i = 1; i <= maskLen; i++) {
            sb.append(maskChar);
            if (i % 4 == 0) {
                sb.append(split);
            }
        }
        if ((i-1) % 4 != 0) {
            sb.append(split);
        }
        sb.append(pan.substring(pan.length() - 4));
        return sb.toString();
    }
}

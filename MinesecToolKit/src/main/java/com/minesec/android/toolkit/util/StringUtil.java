package com.minesec.android.toolkit.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    /**
     * @param str
     * @return String
     */
    public static boolean isNullWithTrim(String str) {
        return str == null || str.trim().equals("")||str.trim().equals("null");
    }

    /**
     * @param cardNo
     * @param prefix
     * @param suffix
     * @return String
     */
    public static String getSecurityNum(String cardNo, int prefix, int suffix) {
        if (null==cardNo) return null;
        StringBuffer cardNoBuffer = new StringBuffer();
        int len = prefix + suffix;
        if ( cardNo.length() > len) {
            cardNoBuffer.append(cardNo.substring(0, prefix));
            for (int i = 0; i < cardNo.length() - len; i++) {
                cardNoBuffer.append("*");
            }
            cardNoBuffer.append(cardNo.substring(cardNo.length() - suffix, cardNo.length()));
        }
        return cardNoBuffer.toString();
    }

    public static String TwoWei(double s){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(s);
    }

    /**
     * @param amount
     * @return  0.00
     */
    public static String TwoWei(String amount){
        DecimalFormat df = new DecimalFormat("0.00");
        double d = 0;
        if(!StringUtil.isNullWithTrim(amount))
            d = Double.parseDouble(amount)/100;
        return df.format(d);
    }

    /**
     *
     * @param date   20160607152954
     * @param oldPattern  yyyyMMddHHmmss
     * @param newPattern yyyy-MM-dd HH:mm:ss
     * @return 2016-06-07 15:29:54
     */
    public static String StringPattern(String date, String oldPattern,
                                       String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);
        Date d = null;
        try {
            d = sdf1.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf2.format(d);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}

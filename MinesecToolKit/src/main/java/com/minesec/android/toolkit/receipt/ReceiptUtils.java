package com.minesec.android.toolkit.receipt;

import com.minesec.android.toolkit.util.StringUtils;

import java.util.List;

/**
 * @author eric.song
 * @since 2022/7/28 9:48
 */
public class ReceiptUtils {
    public static int determineFontSize(List<String> lines) {
        for (String line : lines) {
            if (line != null && line.length() > 32) {
                return TextRow.FONT_SIZE_SMALL;
            }
        }
        return TextRow.FONT_SIZE_NORMAL;
    }

    public static String clearChars(String content,String...sub){
        String result = content;
        for(String subChar : sub){
            if(StringUtils.isEmpty(subChar)){
                continue;
            }
            result = result.replaceAll(subChar,"");
        }
        return result;
    }
}

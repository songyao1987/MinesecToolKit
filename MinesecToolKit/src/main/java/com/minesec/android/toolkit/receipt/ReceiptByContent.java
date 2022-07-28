package com.minesec.android.toolkit.receipt;

import com.minesec.android.toolkit.text.Base64;

import java.util.Arrays;
import java.util.List;

/**
 * @author eric.song
 * @since 2022/7/28 9:55
 */
class ReceiptByContent extends ReceiptByPrintLines {

    private static List<String> toPrintLines(String content) {
        String raw = Base64.getDecoder().decodeToString(content);
        String[] lines = raw.split("\n");
        return Arrays.asList(lines);
    }

    ReceiptByContent(String content) {
        super(toPrintLines(content));
    }

}

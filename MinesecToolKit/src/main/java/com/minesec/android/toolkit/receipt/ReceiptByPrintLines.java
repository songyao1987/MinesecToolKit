package com.minesec.android.toolkit.receipt;

import com.minesec.android.toolkit.util.StringUtils;

import java.net.URLDecoder;
import java.util.List;

/**
 * @author eric.song
 * @since 2022/7/28 9:55
 */
public class ReceiptByPrintLines implements ReceiptBuilder{
    private final List<String> lines;

    ReceiptByPrintLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Receipt build() {
        Receipt receipt = new Receipt();
        int fontSize = ReceiptUtils.determineFontSize(lines);
        for (String line : lines) {
            if(line.contains("@QR@")){
                String decodeUrl = decodeUrl(line);
                if(!StringUtils.isEmpty(decodeUrl)) {
                    receipt.append(new QrRow(decodeUrl, 200));
                }
            } else if (line.startsWith("@1@")) {
                receipt.append(new TextRow(line.substring(3)).fontSize(TextRow.FONT_SIZE_NORMAL));
            } else if (line.startsWith("@2@")) {
                receipt.append(new TextRow(line.substring(3)).fontSize(TextRow.FONT_SIZE_LARGE).fontStyle(TextRow.FONT_STYLE_BOLD));

            } else {
                receipt.append(new TextRow(line)).fontSize(fontSize);
            }
        }
        return receipt;
    }
    private String decodeUrl(String encodeUrl){
        if(StringUtils.isEmpty(encodeUrl)){
            return "";
        }
        try {
            return  URLDecoder.decode(ReceiptUtils.clearChars(encodeUrl,"\\{@QR@","@QR@\\}","@QR@"),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

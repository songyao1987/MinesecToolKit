package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * @author eric.song
 * @since 2022/7/28 9:44
 */
public class QrUtils {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    public static final int DEFAULT_SIZE = 300;
    public static final int DEFAULT_MARGIN = 2;

    public static Bitmap encode(String content) {
        return encode(content, DEFAULT_SIZE);
    }

    @SuppressWarnings("WeakerAccess")
    public static Bitmap encode(String content, int size) {
        return encode(content, size, DEFAULT_MARGIN);
    }

    @SuppressWarnings("WeakerAccess")
    public static Bitmap encode(String content, int size, int margin) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, margin);
        //hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(content, BarcodeFormat.QR_CODE, size, size, hints);

            int w = matrix.getWidth();
            int h = matrix.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                }
            }
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

            return bitmap;
        } catch (WriterException ignore) {
            return bitmap;
        }
    }
}

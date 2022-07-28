package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author eric.song
 * @since 2022/7/28 9:40
 */
public class QrRow extends Row implements Align{
    private int align = ALIGN_CENTER;

    private final String text;
    private final int size;
    private final int margin;

    public QrRow(String text) {
        this(text, QrUtils.DEFAULT_SIZE);
    }

    public QrRow(String text, int size) {
        this(text, size, QrUtils.DEFAULT_MARGIN);
    }

    @SuppressWarnings("WeakerAccess")
    public QrRow(String text, int size, int margin) {
        super();
        this.text = text;
        this.size = size;
        this.margin = margin;
    }

    public int getAlign() {
        return this.align;
    }

    public String getText() {
        return this.text;
    }

    public int getSize() {
        return size;
    }

    public int getMargin() {
        return margin;
    }

    public Bitmap getBitmap() {
        return QrUtils.encode(text, size, margin);
    }

    public QrRow align(int algin) {
        this.align = algin;
        return this;
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        Bitmap bitmap = getBitmap();
        Rect src = new Rect(0, 0, measureWidth(), measureHeight());
        Rect dst = new Rect(src);
        switch (align) {
            case ALIGN_LEFT:
                dst.offset(0, bounds.top);
                break;
            case ALIGN_CENTER:
                dst.offset(bounds.centerX() - src.centerX(), bounds.top);
                break;
            case ALIGN_RIGHT:
                dst.offset(bounds.width() - dst.width(), bounds.top);
                break;
            default:
                throw new IllegalArgumentException("align=" + align);
        }
        canvas.drawBitmap(bitmap, src, dst, getPaint());
    }

    @Override
    public int measureWidth() {
        return size;
    }

    @Override
    public int measureHeight() {
        return size;
    }
}

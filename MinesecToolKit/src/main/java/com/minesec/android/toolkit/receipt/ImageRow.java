package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.minesec.android.toolkit.util.Assertion;

/**
 * @author eric.song
 * @since 2022/7/28 9:52
 */
public class ImageRow extends Row implements Align {

    private final Bitmap bitmap;
    private final int width;
    private final int height;

    private int align = ALIGN_CENTER;

    public ImageRow(Bitmap bitmap) {
        this(bitmap, bitmap.getWidth());
    }

    public ImageRow(Bitmap bitmap, int maxWidth) {
        super();
        Assertion.notNull(bitmap, "bitmap");
        this.bitmap = bitmap;
        boolean zoomOut = bitmap.getWidth() > maxWidth;
        if (zoomOut) {
            this.width = maxWidth;
            this.height = maxWidth * bitmap.getHeight() / bitmap.getWidth();
        } else {
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
        }
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getAlign() {
        return this.align;
    }

    public ImageRow align(int algin) {
        this.align = algin;
        return this;
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
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
        return this.width;
    }

    @Override
    public int measureHeight() {
        return this.height;
    }
}

package com.minesec.android.toolkit.receipt;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author eric.song
 * @since 2022/7/28 9:42
 */
public abstract class Row implements Drawable, Measurable{
    protected Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        customize(paint);
        return paint;
    }

    protected void customize(Paint paint) {}

    @Override
    public Rect getBounds() {
        return new Rect(0, 0, measureWidth(), measureHeight());
    }
}

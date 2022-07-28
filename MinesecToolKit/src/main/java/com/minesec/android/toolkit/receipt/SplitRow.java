package com.minesec.android.toolkit.receipt;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;

/**
 * @author eric.song
 * @since 2022/7/28 9:46
 */
public class SplitRow extends Row {
    public static final int STYLE_SOLID = 0;
    public static final int STYLE_DASHED = 1;

    private final int style;
    private final int height;

    private boolean paging = false;

    public SplitRow() {
        this(STYLE_DASHED);
    }

    public SplitRow(int style) {
        this(style, 24);
    }

    public SplitRow(int style, int height) {
        this.style = style;
        this.height = height;
    }

    public int getStyle() {
        return this.style;
    }

    public boolean isPaging() {
        return this.paging;
    }

    public SplitRow enablePaging() {
        this.paging = true;
        return this;
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        Paint paint = getPaint();
        canvas.drawLine(bounds.left, bounds.centerY(), bounds.right, bounds.centerY(), paint);
        if (paging) {
            paint.setStrokeWidth(2);
            paint.setPathEffect(new PathEffect());
            int offset = bounds.width() / 4;
            canvas.drawLine(bounds.left + offset, bounds.top,
                    bounds.left + offset + bounds.height(), bounds.bottom, paint);
            canvas.drawLine(bounds.left + offset, bounds.bottom,
                    bounds.left + offset + bounds.height(), bounds.top, paint);
            canvas.drawLine(bounds.right - offset, bounds.top,
                    bounds.right - offset - bounds.height(), bounds.bottom, paint);
            canvas.drawLine(bounds.right - offset, bounds.bottom,
                    bounds.right - offset - bounds.height(), bounds.top, paint);
        }
    }

    @Override
    public int measureWidth() {
        return 1;
    }

    @Override
    public int measureHeight() {
        return this.height;
    }

    @Override
    protected void customize(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        if (STYLE_DASHED == style) {
            paint.setPathEffect(new DashPathEffect(new float[] {8, 4}, 0));
        }
    }

}

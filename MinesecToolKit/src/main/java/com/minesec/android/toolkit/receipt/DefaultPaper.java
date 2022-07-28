package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author eric.song
 * @since 2022/7/28 10:02
 */
public class DefaultPaper implements Paper{
    private final Document document;
    private final int width;
    private final Bitmap.Config config;

    public static int PRINTER_PAPER_WIDTH = 372;


    public static int DEFAULT_PAPER_WIDTH = 720;


    public static int DEFAULT_PAPER_PADDING_VERTICAL = 12;


    public static int DEFAULT_PAPER_PADDING_HORIZONTAL = 8;


    public static int DEFAULT_PAPER_LINE_SPACING = 6;

    private final int paddingLeft = DEFAULT_PAPER_PADDING_HORIZONTAL;
    private final int paddingRight = DEFAULT_PAPER_PADDING_HORIZONTAL;
    private final int paddingTop = DEFAULT_PAPER_PADDING_VERTICAL;
    private final int paddingBottom = DEFAULT_PAPER_PADDING_VERTICAL;
    private final int lineSpacing = DEFAULT_PAPER_LINE_SPACING;

    public DefaultPaper(Document document) {
        this(document, DEFAULT_PAPER_WIDTH);
    }

    public DefaultPaper(Document document, int width) {
        this(document, width, Bitmap.Config.ARGB_8888);
    }

    public DefaultPaper(Document document, int width, Bitmap.Config config) {
        this.document = document;
        this.width = width;
        this.config = config;
    }

    public Bitmap createBitmap(Rect bounds, Bitmap.Config config) {
        return Bitmap.createBitmap(bounds.width(), bounds.height(), config);
    }

    @Override
    public Bitmap render() {
        Rect bounds = getBounds();
        Bitmap bitmap = createBitmap(bounds, this.config);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas, bounds);
        return bitmap;
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        if (!document.isEmpty()) {
            int left = paddingLeft + bounds.left;
            int right = bounds.right - paddingRight;
            int top = bounds.top + paddingTop;
            int bottom;
            for (Row row : document.getRows()) {
                bottom = top + row.measureHeight();
                Rect rc = new Rect(left, top, right, bottom);
                row.draw(canvas, rc);
                top += rc.height() + lineSpacing;
            }
        }
    }

    @Override
    public int measureWidth() {
        return this.width;
    }

    @Override
    public int measureHeight() {
        int height = paddingTop + paddingBottom;
        if (document.isEmpty()) {
            return height;
        }
        for (Row row :document.getRows()) {
            height += row.measureHeight();
            height += lineSpacing;
        }
        return height - lineSpacing;
    }

    @Override
    public Rect getBounds() {
        return new Rect(0, 0, measureWidth(), measureHeight());
    }

}

package com.minesec.android.toolkit.receipt;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;

/**
 * @author eric.song
 * @since 2022/7/28 9:47
 */
public class TextRow extends Row implements Align{
    public static final int FONT_SIZE_SMALL = 0;
    public static final int FONT_SIZE_NORMAL = 1;
    public static final int FONT_SIZE_LARGE = 2;

    public static final int FONT_STYLE_NORMAL = 0;
    public static final int FONT_STYLE_ITALIC = 1;
    public static final int FONT_STYLE_BOLD = 2;

    public static int DEFAULT_FONT_SIZE_SMALL = 24;

    public static int DEFAULT_FONT_SIZE_NORMAL = 36;

    public static int DEFAULT_FONT_SIZE_LARGE = 48;

    private final String text;

    private int align = ALIGN_LEFT;
    private int fontSize = FONT_SIZE_NORMAL;
    private int fontStyle = FONT_STYLE_NORMAL;
    private boolean underline;

    public TextRow(String text) {
        super();
        this.text = TextUtils.isEmpty(text) ? " " : text;
    }

    public String getText() {
        return this.text;
    }

    public int getAlign() {
        return this.align;
    }

    public TextRow align(int algin) {
        this.align = algin;
        return this;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public int getFontStyle() {
        return this.fontStyle;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public TextRow fontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public TextRow fontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public TextRow underline() {
        this.underline = true;
        return this;
    }

    private boolean isEmpty() {
        return text == null || text.trim().isEmpty();
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {
        if (!isEmpty()) {
            Paint paint = getPaint();
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            int x = getX(bounds, getAlign());
            canvas.drawText(text, x, bounds.top + fm.leading - fm.ascent, paint);
        }
    }

    @Override
    public int measureWidth() {
        Paint paint = getPaint();
        return (int)paint.measureText(isEmpty() ? " " : text);
    }

    @Override
    public int measureHeight() {
        Paint paint = getPaint();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        return fm.descent - fm.ascent;
    }

    @Override
    protected void customize(Paint paint) {
        paint.setTextAlign(getPaintAlign());
        paint.setTextSize(getPaintTextSize());
        paint.setTypeface(getPaintTypeface());
        paint.setUnderlineText(this.underline);
    }

    private int getX(Rect bounds, int align) {
        switch (align) {
            case ALIGN_LEFT:
                return bounds.left;
            case ALIGN_CENTER:
                return bounds.centerX();
            case ALIGN_RIGHT:
                return bounds.right;
            default:
                throw new IllegalArgumentException("align=" + getAlign());
        }
    }

    private Paint.Align getPaintAlign() {
        switch (this.align) {
            case ALIGN_LEFT:
                return Paint.Align.LEFT;
            case ALIGN_CENTER:
                return Paint.Align.CENTER;
            case ALIGN_RIGHT:
                return Paint.Align.RIGHT;
            default:
                throw new IllegalArgumentException("align=" + getAlign());
        }
    }

    private int getPaintTextSize() {
        switch (fontSize) {
            case FONT_SIZE_NORMAL:
                return DEFAULT_FONT_SIZE_NORMAL;
            case FONT_SIZE_SMALL:
                return DEFAULT_FONT_SIZE_SMALL;
            case FONT_SIZE_LARGE:
                return DEFAULT_FONT_SIZE_LARGE;
            default:
                throw new IllegalArgumentException("fontSize=" + fontSize);
        }
    }

    private Typeface getPaintTypeface() {
        switch (fontStyle) {
            case FONT_STYLE_NORMAL:
                return Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
            case FONT_STYLE_BOLD:
                return Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
            case FONT_STYLE_ITALIC:
                return Typeface.create(Typeface.MONOSPACE, Typeface.ITALIC);
            default:
                throw new IllegalArgumentException("fontStyle=" + fontStyle);
        }
    }

}

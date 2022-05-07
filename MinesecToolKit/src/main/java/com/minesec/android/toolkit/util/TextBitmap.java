package com.minesec.android.toolkit.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class TextBitmap {

    public static final TextStyle DEFAULT = TextStyle.builder().build();

    public static Bitmap fromText(String text, TextStyle style) {
        Paint paint = new Paint();
        if (style.textSize != null) {
            paint.setTextSize(style.textSize);
        }
        paint.setTextAlign(style.textAlign);
        paint.setColor(style.color);
        paint.setTypeface(style.typeface);

        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        int width = (int)paint.measureText(text) + style.padding * 2;
        int height = fm.descent - fm.ascent + style.padding * 2;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, style.padding, fm.leading - fm.ascent + style.padding, paint);
        canvas.save();

        return bitmap;
    }

    public static Drawable toDrawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(null, bitmap);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public static final class TextStyle {

        private final Float textSize;
        private final Paint.Align textAlign;
        private final int padding;
        private final int color;
        private final Typeface typeface;

        public static Builder builder() {
            return new Builder();
        }

        private TextStyle(Float textSize, Paint.Align align, int padding, int color, Typeface typeface) {
            this.textSize = textSize;
            this.textAlign = align;
            this.padding = padding;
            this.color = color;
            this.typeface = typeface;
        }

        public static final class Builder {

            private Float size;
            private Paint.Align align = Paint.Align.LEFT;
            private int padding = 4;
            private int color = Color.BLACK;
            private Typeface typeface = Typeface.DEFAULT;

            public Builder size(float size) {
                this.size = size;
                return this;
            }

            public Builder align(Paint.Align align) {
                this.align = align;
                return this;
            }

            public Builder padding(int padding) {
                this.padding = padding;
                return this;
            }

            public Builder color(int color) {
                this.color = color;
                return this;
            }

            public Builder typeface(Typeface typeface) {
                this.typeface = typeface;
                return this;
            }

            public TextStyle build() {
                return new TextStyle(size, align, padding, color, typeface);
            }

        }
    }
}

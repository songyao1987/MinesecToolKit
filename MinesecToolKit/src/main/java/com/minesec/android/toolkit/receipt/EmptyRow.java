package com.minesec.android.toolkit.receipt;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author eric.song
 * @since 2022/7/28 9:51
 */
public class EmptyRow extends Row {

    private final int height;

    private final int lines;

    public EmptyRow() {
        this(24, 1);
    }

    public EmptyRow(int lines) {
        this(24, lines);
    }

    public EmptyRow(int height, int lines) {
        this.height = height;
        this.lines = lines;
    }

    public int getLines() {
        return lines;
    }

    @Override
    public void draw(Canvas canvas, Rect bounds) {

    }

    @Override
    public int measureWidth() {
        return 1;
    }

    @Override
    public int measureHeight() {
        return height * lines;
    }
}

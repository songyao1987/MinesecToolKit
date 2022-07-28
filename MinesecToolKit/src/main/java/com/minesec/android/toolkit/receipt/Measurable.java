package com.minesec.android.toolkit.receipt;

import android.graphics.Rect;

/**
 * @author eric.song
 * @since 2022/7/28 9:43
 */
public interface Measurable {
    int measureWidth();

    int measureHeight();

    Rect getBounds();
}

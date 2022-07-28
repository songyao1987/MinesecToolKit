package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;

/**
 * @author eric.song
 * @since 2022/7/28 10:01
 */
public interface Paper extends Measurable, Drawable {

    Bitmap render();

}
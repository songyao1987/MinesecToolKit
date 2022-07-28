package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;

/**
 * @author eric.song
 * @since 2022/7/28 9:53
 */
public interface Printer {

    /**
     * new DefaultPaper(document).render(document);
     */
    Bitmap preview(Document document);

    void print(Document document);

}

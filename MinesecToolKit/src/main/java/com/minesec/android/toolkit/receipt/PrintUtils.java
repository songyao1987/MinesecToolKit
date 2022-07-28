package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author eric.song
 * @since 2022/7/28 9:52
 */
public abstract class PrintUtils {

    public static Bitmap preview(Document document) {
        Printer printer = discover();
        return printer.preview(document);
    }

    public static void print(Document document) {
        Printer printer = discover();
        printer.print(document);
    }

    private static Printer discover() {
        ServiceLoader<Printer> loader = ServiceLoader.load(Printer.class);
        Iterator<Printer> it = loader.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("No printer: " + Printer.class.getName());
        }
        Printer printer = it.next();
        if (it.hasNext()) {
            throw new RuntimeException("More printer detected: " + it.next().getClass().getName());
        }
        return printer;
    }

}

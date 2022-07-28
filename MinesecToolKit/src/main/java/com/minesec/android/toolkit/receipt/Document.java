package com.minesec.android.toolkit.receipt;

import android.graphics.Bitmap;

import com.minesec.android.toolkit.functional.Action;
import com.minesec.android.toolkit.util.Assertion;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author eric.song
 * @since 2022/7/28 9:50
 */
public class Document {
    private final List<Row> rows = new LinkedList<>();

    boolean isEmpty() {
        return rows.isEmpty();
    }

    List<Row> getRows() {
        return this.rows;
    }

    public void forEach(Action<Row> action) {
        for (Row row : rows) {
            action.accept(row);
        }
    }

    public <T extends Row> T append(T row) {
        Assertion.notNull(row, "row");
        rows.add(row);
        return row;
    }

    public Document append(Row...rows) {
        Assertion.notNull(rows, "rows");
        this.rows.addAll(Arrays.asList(rows));
        return this;
    }

    public TextRow append(String text) {
        return append(new TextRow(text));
    }

    public SplitRow split(int style) {
        return append(new SplitRow(style));
    }

    public SplitRow split() {
        return append(new SplitRow());
    }

    public EmptyRow appendEmptyLine() {
        return append(new EmptyRow());
    }

    public EmptyRow appendEmptyLines(int lines) {
        return append(new EmptyRow(lines));
    }

    public ImageRow append(Bitmap bitmap) {
        return append(new ImageRow(bitmap));
    }

    public QrRow appendQr(String text) {
        return append(new QrRow(text));
    }

    public QrRow appendQr(String text, int size) {
        return append(new QrRow(text, size));
    }

    public QrRow appendQr(String text, int size, int margin) {
        return append(new QrRow(text, size, margin));
    }

    public Bitmap preview() {
        return PrintUtils.preview(this);
    }

    public void print() {
        PrintUtils.print(this);
    }
}
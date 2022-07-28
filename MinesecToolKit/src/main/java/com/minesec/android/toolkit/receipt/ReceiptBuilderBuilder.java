package com.minesec.android.toolkit.receipt;

import android.content.Context;

import java.util.Currency;

/**
 * @author eric.song
 * @since 2022/7/28 10:00
 */
public class ReceiptBuilderBuilder {

    private int receiptType;
    private PrintingCheck check;
    private Currency currency;
    private Context context;
    private boolean withPayment;

    public ReceiptBuilderBuilder setReceiptType(int receiptType) {
        this.receiptType = receiptType;
        return this;
    }

    public ReceiptBuilderBuilder setPrintingCheck(PrintingCheck check) {
        this.check = check;
        return this;
    }

    public ReceiptBuilderBuilder setCurrency(String currencyCode) {
        this.currency = Currency.getInstance(currencyCode);
        return this;
    }

    public ReceiptBuilderBuilder setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public ReceiptBuilderBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public ReceiptBuilderBuilder setWithPayment(boolean withPayment) {
        this.withPayment = withPayment;
        return this;
    }

    public ReceiptBuilder create() {
        return new ReceiptByContent(check.getReceiptContent());
    }
}

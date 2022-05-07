package com.minesec.android.toolkit.rule;

public class DefaultRule implements Rule<String> {
    @Override
    public boolean accept(String value) {
        return true;
    }
}
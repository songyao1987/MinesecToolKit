package com.minesec.android.toolkit.rule;

public class PrefixRule implements Rule<String> {

    private final String prefix;

    public PrefixRule(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean accept(String value) {
        if (prefix == null) {
            return true;
        }
        if (value == null) {
            return false;
        }
        return value.startsWith(prefix);
    }

}

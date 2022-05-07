package com.minesec.android.toolkit.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmojiFilter implements InputFilter {

    private static final Pattern pattern = Pattern.compile(
            "[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return "";
        }
        return null;
    }

}

package com.minesec.android.toolkit.keyboard;

/**
 * @author eric.song
 * @since 2023/3/23 12:13
 */
import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.minesec.android.toolkit.R;
import com.minesec.android.toolkit.util.StringUtil;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author eric.song
 * @since 2022/7/26 9:32
 */
public class KeyboardHelper {
    public static final int INPUT_TYPE_NUMBER = 0;
    public static final int INPUT_TYPE_NUMBER_DECIMAL = 1;
    public static final int INPUT_TYPE_CHAR_NUMBER = 3;
    public static final int INPUT_TYPE_PASSWORD = 16;

    private Context mContext;
    private Keyboard mKeyboard;
    private KeyboardView mKeyboardView;

    private boolean mIsRandom;
    private EditText mEditText;
    private boolean mIsShowAlways = true;

    private KeyboardHelper.OnOkClickListener mOkClickListener;
    private KeyboardHelper.OnCancelClickListener mCancelClickListener;
    private final KeyboardView.OnKeyboardActionListener mKeyboardActionListener = new KeyboardView
            .OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (mEditText == null) return;
            Editable text = mEditText.getText();
            int selectionStart = mEditText.getSelectionStart();
            int selectionEnd = mEditText.getSelectionEnd();

            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (text != null && text.length() > 0) {
                        if (selectionStart != selectionEnd) {
                            text.delete(selectionStart, selectionEnd);
                        } else if (selectionStart > 0) {
                            text.delete(selectionStart - 1, selectionStart);
                        }
                        if (text.length() < 6) {
                            text.insert(2,"0");
                        }
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    if (text != null) {
                        text.insert(selectionStart, "0");
                        text.insert(selectionStart, "0");
                    }
                    break;
                case Keyboard.KEYCODE_ALT:
                    // do nothing
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    hideNumberKeyboard();
                    if (mCancelClickListener != null) {
                        mCancelClickListener.onClick();
                    }
                    break;
                case Keyboard.KEYCODE_DONE:
                    hideNumberKeyboard();

                    onKeyDone();
                    break;
                default:
                    text.insert(selectionStart, Character.toString(((char) primaryCode)));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
    private int mType;

    private void onKeyDone() {
        switch (mType) {
            case INPUT_TYPE_NUMBER_DECIMAL:
                String result = mEditText.getText().toString().trim();

                if (!result.equals("")) {
                    double data;
                    // Enter decimal
                    if (result.contains("."))
                        data = Double.parseDouble(result + '0');
                    else
                        data = Double.parseDouble(result);

                    if (data != 0.0) {
                        DecimalFormat format = new DecimalFormat("0.00");
                        result = format.format(data);
                        mOkClickListener.onClick(result.replace(".", ""));
                    }

                }
                break;
            case INPUT_TYPE_PASSWORD:
            case INPUT_TYPE_NUMBER:
            case INPUT_TYPE_CHAR_NUMBER:
                result = mEditText.getText().toString().trim();
                if (!StringUtil.isNullWithTrim(result)) {
                    mOkClickListener.onClick(result);
                }
                break;
            default:
                break;
        }
    }

    public KeyboardHelper(Context context, KeyboardView keyboardView) {
        mContext = context;
        mKeyboard = new Keyboard(context, R.xml.payment_number_keyboard);
        mKeyboardView = keyboardView;
    }

    public void setRandom(boolean isRandom) {
        mIsRandom = isRandom;
    }

    public void setShowAlways(boolean isShowAlways) {
        mIsShowAlways = isShowAlways;
    }

    public void onAttach(EditText editText, int type) {
        mEditText = editText;
        mEditText.getText().clear();
        hideSystemKeyboard(editText);
        showNumberKeyboard();
        mType = type;
        switch (type) {
            default:
            case INPUT_TYPE_NUMBER:
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUT_TYPE_NUMBER_DECIMAL:
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.setRawInputType(Configuration.KEYBOARD_12KEY);
                editText.append("0.00");
                editText.addTextChangedListener(mTextWatcher);
                break;
            case INPUT_TYPE_CHAR_NUMBER:
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case INPUT_TYPE_PASSWORD:
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }

    private void hideSystemKeyboard(EditText editText) {
        editText.setShowSoftInputOnFocus(false);
        // Hide system soft keyboard if already shown.
        InputMethodManager imm =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void showNumberKeyboard() {
        if (mIsRandom) {
            randomKeyboardNumber();
        }
        mKeyboardView.setKeyboard(mKeyboard);

        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setOnKeyboardActionListener(mKeyboardActionListener);
    }

    private void hideNumberKeyboard() {
        if (mKeyboardView.getVisibility() == View.VISIBLE && !mIsShowAlways) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    private void randomKeyboardNumber() {
        List<Keyboard.Key> keyList = mKeyboard.getKeys();
        // find out number key
        ArrayList<Keyboard.Key> numberKeyList = new ArrayList<>();
        ArrayList<Keyboard.Key> shuffledKeyList = new ArrayList<>();
        for (Keyboard.Key key : keyList) {
            if (key.codes.length > 0 && key.codes[0] >= '0' && key.codes[0] <= '9') {
                numberKeyList.add(key);
                shuffledKeyList.add(key);
            }
        }
        Collections.shuffle(shuffledKeyList);
        for (int i = 0; i < numberKeyList.size(); i++) {
            String tempLabel = numberKeyList.get(i).label.toString();
            numberKeyList.get(i).label = shuffledKeyList.get(i).label;
            shuffledKeyList.get(i).label = tempLabel;
            int codes = numberKeyList.get(i).codes[0];
            numberKeyList.get(i).codes[0] = shuffledKeyList.get(i).codes[0];
            shuffledKeyList.get(i).codes[0] = codes;
        }
    }

    public void setOnOkClickListener(KeyboardHelper.OnOkClickListener okClickListener) {
        mOkClickListener = okClickListener;
    }

    public void setOnCancelClickListener(KeyboardHelper.OnCancelClickListener cancelClickListener) {
        mCancelClickListener = cancelClickListener;
    }

    public void release() {
        if (mEditText != null) {
            mEditText.removeTextChangedListener(mTextWatcher);
        }
        mEditText = null;

        mContext = null;
        mKeyboard = null;
        mKeyboardView = null;

        mOkClickListener = null;
        mCancelClickListener = null;
    }

    public interface OnOkClickListener {
        void onClick(String data);
    }

    public interface OnCancelClickListener {
        void onClick();
    }

    public String getAmountInput() {
        hideNumberKeyboard();
        String result = mEditText.getText().toString().trim();

        if (!result.equals("")) {
            double data;
            if (result.contains("."))
                data = Double.parseDouble(result + '0');
            else
                data = Double.parseDouble(result);
            if (data != 0.0) {
                DecimalFormat format = new DecimalFormat("0.00");
                result = format.format(data);
                result = result.replace(".", "");
            }

        }
        return result;
    }

    boolean isFormatting = false;

    private final AmountTextWatcher mTextWatcher = new AmountTextWatcher();

    private class AmountTextWatcher implements TextWatcher {
        private static final String POINTER = ".";
        private static final String ZERO = "0";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        private boolean isManual = true;

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void afterTextChanged(Editable s) {
            //"after text changed:" + s
            if (isManual) {
                isManual = false;
                String text = s.toString();
                if (TextUtils.isEmpty(text)) {
                    isManual = true;
                    return;
                }
                if (text.contains(" ")) {
                    text = text.substring(text.indexOf(" ") + 1);
                }

                //"before text:" + text
                if (!text.contains(POINTER)) {
                    s.insert(0, "0.0");
                    //"insert pointer:" + s.toString()
                } else {
                    text = s.toString();
                    int index = text.indexOf(POINTER);
                    if (text.length() - index > 3) {
                        s.delete(index, index + 1);
                        s.insert(text.length() - 3, POINTER);
                        text = s.toString();
                        if (text.startsWith(ZERO) && text.charAt(1) != '.') {
                            s.delete(0, 1);
                        }
                        //"move pointer to right:" + s.toString()
                    } else if (text.length() - index < 3) {
                        // if we have enough characters before pointer
                        if (index > 1) {
                            s.delete(index, index + 1);
                            s.insert(text.length() - 3, POINTER);
                        } else {
                            if (!text.startsWith(ZERO)) {
                                s.delete(index, index + 1);
                                s.insert(0, ZERO + POINTER);
                            } else {
                                // otherwise fill with zero
                                s.insert(index + 1, ZERO);
                            }
                        }
                        //"move pointer to left:" + s.toString()
                    }
                }
                text = s.toString();
                String str = text;
                if (str.startsWith("$")) {
                    str = str.substring(2);
                    if (str.startsWith(ZERO) && str.charAt(1) != '.') {
                        s.delete(2,3);
                    }
                } else {
                    if (text.startsWith(ZERO) && text.charAt(1) != '.') {
                        s.delete(0, 1);
                    }
                }
                if (!isFormatting && s.length() > 0) {
                    isFormatting = true;
                    String formattedText = "$ " + s.toString();
                    mEditText.removeTextChangedListener(this);
                    SpannableString spannableString = new SpannableString(formattedText);
                    spannableString.setSpan(new ForegroundColorSpan(mContext.getColor(R.color.colorAccent)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mEditText.setText(spannableString);
                    mEditText.setSelection(formattedText.length());
                    mEditText.addTextChangedListener(this);
                }

                isManual = true;
                //"after text:" + text
            }
        }
    }

}

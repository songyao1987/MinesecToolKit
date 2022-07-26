package com.minesec.android.toolkit.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.minesec.android.toolkit.R;

import java.util.List;

/**
 * @author eric.song
 * @since 2022/7/26 9:24
 */
public class NumberKeyboardView extends KeyboardView {
    private Context mContext;
    private Rect mRect;
    private Paint mPaint;

    public NumberKeyboardView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initDrawing();
    }

    public NumberKeyboardView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initDrawing();
    }

    private void initDrawing() {
        mRect = new Rect();
        mPaint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        List<Keyboard.Key> keys = null;
        if (keyboard != null) {
            keys = keyboard.getKeys();
        }
        if (keys != null) {
            for (Keyboard.Key key : keys) {
                drawKeyBackground(R.drawable.bg_keyboard, canvas, key);
                drawText(canvas, key);
            }
        }
    }

    private void drawKeyBackground(int resId, Canvas canvas, Keyboard.Key key) {
        //noinspection deprecation
        Drawable drawable = mContext.getResources().getDrawable(resId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes.length > 0 && key.codes[0] != 0) {
            drawable.setState(drawableState);
        }
//        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
//        drawable.draw(canvas);
    }

    private void drawText(Canvas canvas, Keyboard.Key key) {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
        if (key.label != null) {
            String label = key.label.toString();
            int labelTextSize = 48;
            int keyTextSize = 64;
//            Field field;
//            try {
//                field = KeyboardView.class.getDeclaredField("mLabelTextSize");
//                field.setAccessible(true);
//                labelTextSize = ((int) field.get(this));
//                field = KeyboardView.class.getDeclaredField("mKeyTextSize");
//                field.setAccessible(true);
//                keyTextSize = ((int) field.get(this));
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
            if (!TextUtils.isDigitsOnly(label)) {
                mPaint.setColor(Color.parseColor("#e60000"));
                mPaint.setTextSize(labelTextSize);
                mPaint.setTypeface(Typeface.DEFAULT);
            } else {
                mPaint.setColor(Color.BLACK);
                mPaint.setTextSize(keyTextSize);
                mPaint.setTypeface(Typeface.DEFAULT);
            }
            mPaint.getTextBounds(label, 0, label.length(), mRect);
            canvas.drawText(label,
                    key.x + key.width / 2,
                    key.y + key.height / 2 + mRect.height() / 2,
                    mPaint);
        } else if (key.icon != null) {
            key.icon.setBounds(
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2,
                    key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2
                            + key.icon.getIntrinsicWidth(),
                    key.y + (key.height - key.icon.getIntrinsicHeight()) / 2
                            + key.icon.getIntrinsicHeight()
            );
            key.icon.draw(canvas);
        }
    }
}

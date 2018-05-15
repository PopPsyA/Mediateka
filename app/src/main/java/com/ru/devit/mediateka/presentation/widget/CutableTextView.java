package com.ru.devit.mediateka.presentation.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.ru.devit.mediateka.R;

public class CutableTextView extends AppCompatTextView {

    private static final int SHORT_TEXT_LINES = 5;
    private static final int FULL_TEXT_LINES = Integer.MAX_VALUE;

    private boolean clicked = false;

    public CutableTextView(Context context) {
        super(context);
        init(context);
    }

    public CutableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CutableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        setMaxLines(SHORT_TEXT_LINES);
        super.setText(text, type);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if (!clicked) {
                setMaxLines(FULL_TEXT_LINES);
                clicked = true;
            } else {
                setMaxLines(SHORT_TEXT_LINES);
                clicked = false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void init(Context context) {
        this.setEllipsize(TextUtils.TruncateAt.END);
        this.setClickable(true);
        this.setFocusable(true);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground , typedValue , true);
        this.setBackgroundResource(typedValue.resourceId);
    }
}

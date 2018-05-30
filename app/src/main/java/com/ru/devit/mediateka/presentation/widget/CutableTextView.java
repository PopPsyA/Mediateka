package com.ru.devit.mediateka.presentation.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.ru.devit.mediateka.R;

public class CutableTextView extends AppCompatTextView {

    private static final int SHORT_TEXT_LINES = 4;
    private static final String PROPERTY_MAX_LINES = "maxLines";

    private boolean clicked = false;
    private boolean isLinesSetted = false;
    private int totalCountLines;

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
        super.setText(text, type);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getMaxLines() != SHORT_TEXT_LINES && !isLinesSetted){
            totalCountLines = getLineCount();
            isLinesSetted = true;
        }
        setMaxLines(SHORT_TEXT_LINES);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if (!clicked) {
                expandAnimation();
                clicked = true;
            } else {
                collapseAnimation();
                clicked = false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void expandAnimation(){
        startMaxLineAnimationChanger(totalCountLines , 0);
    }

    private void collapseAnimation(){
        startMaxLineAnimationChanger(totalCountLines , SHORT_TEXT_LINES);
    }

    private void startMaxLineAnimationChanger(int from , int to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, PROPERTY_MAX_LINES, from , to);
        if (to == 0){
            objectAnimator = ObjectAnimator.ofInt(this , PROPERTY_MAX_LINES , from);
        }
        if (totalCountLines <= 8){
            objectAnimator.setDuration(70);
        }
        objectAnimator.start();
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

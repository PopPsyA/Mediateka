package com.ru.devit.mediateka.presentation.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ru.devit.mediateka.R;

import java.util.Objects;

public class IndicatorView extends View implements ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private Paint mSelectedIndicatorPaint;
    private Paint mDefaultIndicatorPaint;
    private int indicatorCount;
    private int diameter;
    private float size;
    private int currentPosition;
    private float offset;

    public IndicatorView(Context context) {
        super(context);
        init(context , null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context , attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context , attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float distanceBetweenDots = diameter * 1.3f;
        int radius = diameter / 2;
        for (int i = 0; i < indicatorCount; i++){
            float cx = size + (distanceBetweenDots * i);
            canvas.drawCircle(cx , diameter, radius , mDefaultIndicatorPaint);
        }
        float selectedPosition = (distanceBetweenDots * currentPosition);
        float nextPosition = distanceBetweenDots * (currentPosition + 1);

        canvas.drawCircle(size + lerp(selectedPosition , nextPosition , offset) , diameter, radius , mSelectedIndicatorPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec) , measureHeight(heightMeasureSpec));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        offset = positionOffset;
        currentPosition = position;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setUpWithViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        indicatorCount = Objects.requireNonNull(mViewPager.getAdapter()).getCount();
        if (indicatorCount <= 1){
            return;
        }
        requestLayout();
    }

    private float lerp(float firstPosition, float secondPosition , float offset){
        return firstPosition + offset * (secondPosition - firstPosition);
    }

    private int measureWidth(int measureSpec){
        int result = 0;
        int widthMode = MeasureSpec.getMode(measureSpec);
        int widthSize = MeasureSpec.getSize(measureSpec);

        if (widthMode == MeasureSpec.EXACTLY){
            result = widthSize;
        } else {
            if (indicatorCount > 1){
                result = (int) (size * indicatorCount + getPaddingLeft() + getPaddingRight() + diameter / 2);
                if (widthMode == MeasureSpec.AT_MOST){
                    result = Math.min(result , widthSize);
                }
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec){
        int result;
        int heightMode = MeasureSpec.getMode(measureSpec);
        int heightSize = MeasureSpec.getSize(measureSpec);

        if (heightMode == MeasureSpec.EXACTLY){
            result = heightSize;
        } else {
            result = (int) (size + getPaddingTop() + getPaddingBottom() + diameter / 2);
            if (heightMode == MeasureSpec.AT_MOST){
                result = Math.min(result , heightSize);
            }
        }
        return result;
    }

    private void init(Context context , AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.IndicatorView);
        int defaultIndicatorColor = a.getColor(R.styleable.IndicatorView_iv_default_color , ContextCompat.getColor(context , R.color.colorWhite));
        int selectedIndicatorColor = a.getColor(R.styleable.IndicatorView_iv_selected_color , ContextCompat.getColor(context , R.color.colorWhite));
        diameter = a.getDimensionPixelSize(R.styleable.IndicatorView_iv_diametr , getResources().getDimensionPixelSize(R.dimen.indicator_diametr));
        size = diameter * 1.6f;

        mDefaultIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultIndicatorPaint.setColor(defaultIndicatorColor);
        mDefaultIndicatorPaint.setStyle(Paint.Style.STROKE);
        mDefaultIndicatorPaint.setStrokeWidth(2f);
        mSelectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedIndicatorPaint.setColor(selectedIndicatorColor);
        setBackground(ContextCompat.getDrawable(context , R.drawable.bg_indicator_view));
        a.recycle();
    }
}

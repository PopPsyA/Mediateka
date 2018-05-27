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
    private int diametr;
    private int size;
    private int currentPosition;

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
        int radius = diametr / 2;
        for (int i = 0; i < indicatorCount; i++){
            int cx = diametr + (size * i);
            canvas.drawCircle(cx , diametr, radius , mDefaultIndicatorPaint);
        }
        canvas.drawCircle(diametr + (size * currentPosition) , diametr, radius , mSelectedIndicatorPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec) , measureHeight(heightMeasureSpec));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setUpWithViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        indicatorCount = Objects.requireNonNull(mViewPager.getAdapter()).getCount();
        requestLayout();
    }

    private int measureWidth(int measureSpec){
        int result;
        int widthMode = MeasureSpec.getMode(measureSpec);
        int widthSize = MeasureSpec.getSize(measureSpec);

        if (widthMode == MeasureSpec.EXACTLY){
            result = widthSize;
        } else {
            result = size * indicatorCount;
            if (widthMode == MeasureSpec.AT_MOST){
                result = Math.min(result , widthSize);
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
            result = 2 * diametr + getPaddingTop() + getPaddingBottom();
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
        diametr = a.getDimensionPixelSize(R.styleable.IndicatorView_iv_diametr , getResources().getDimensionPixelSize(R.dimen.indicator_diametr));
        size = diametr * 2;

        mDefaultIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultIndicatorPaint.setColor(defaultIndicatorColor);
        mDefaultIndicatorPaint.setStyle(Paint.Style.STROKE);
        mDefaultIndicatorPaint.setStrokeWidth(1.5f);
        mSelectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedIndicatorPaint.setColor(selectedIndicatorColor);
        a.recycle();
    }
}

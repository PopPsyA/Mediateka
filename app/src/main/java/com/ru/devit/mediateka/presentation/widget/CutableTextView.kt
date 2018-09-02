package com.ru.devit.mediateka.presentation.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.TextView

import com.ru.devit.mediateka.R

class CutableTextView : AppCompatTextView {

    companion object {

        private const val SHORT_TEXT_LINES = 4
        private const val PROPERTY_MAX_LINES = "maxLines"
    }

    private var clicked = false
    private var linesRendered = false
    private var totalCountLines: Int = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        super.setText(text, type)
        post {
            if (lineCount > SHORT_TEXT_LINES && !linesRendered) {
                totalCountLines = lineCount
                linesRendered = true
            }
            if (totalCountLines > SHORT_TEXT_LINES) {
                setLines(SHORT_TEXT_LINES)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            clicked = if (!clicked) {
                expandAnimation()
                true
            } else {
                collapseAnimation()
                false
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun expandAnimation() {
        startMaxLineAnimationChanger(totalCountLines, 0)
    }

    private fun collapseAnimation() {
        startMaxLineAnimationChanger(totalCountLines, SHORT_TEXT_LINES)
    }

    private fun startMaxLineAnimationChanger(from: Int, to: Int) {
        if (totalCountLines > SHORT_TEXT_LINES) {
            var objectAnimator = ObjectAnimator.ofInt(this, PROPERTY_MAX_LINES, from, to)
            if (to == 0) {
                objectAnimator = ObjectAnimator.ofInt(this, PROPERTY_MAX_LINES, from)
            }
            if (totalCountLines <= 8) {
                objectAnimator.duration = 70
            }
            objectAnimator.start()
        }
    }

    private fun init(context: Context) {
        this.ellipsize = TextUtils.TruncateAt.END
        this.isClickable = true
        this.isFocusable = true
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackground, typedValue, true)
        this.setBackgroundResource(typedValue.resourceId)
    }
}

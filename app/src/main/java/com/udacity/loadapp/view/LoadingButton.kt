package com.udacity.loadapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.udacity.loadapp.R
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val BUTTON_CORNER_RADIUS = 8f
        private const val SPINNER_RADIUS = 32f
        private const val SPINNER_STROKE_WIDTH = 16f
        private const val SPINNER_START_ANGLE = 270f
        private const val DURATION_ANIMATION_MS = 1500L
    }

    private var widthSize = 0
    private var heightSize = 0

    private var buttonText = "Download"
    private var textOffset = 0f

    private val spinnerRect = RectF()

    private var animatedWidth = 0f
    private var animatedSweepAngle = 0f

    private val valueAnimator = ValueAnimator().apply {
        duration = DURATION_ANIMATION_MS
        addUpdateListener { valueAnimator ->
            animatedWidth = valueAnimator.animatedValue as Float
            animatedSweepAngle = 360f * animatedFraction
            invalidate()
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                buttonState = ButtonState.Completed
            }
        })
    }

    var buttonState: ButtonState
            by Delegates.observable(ButtonState.Completed) { _, _, new ->

                when (new) {

                    ButtonState.Clicked -> {
                        valueAnimator.cancel()
                        animatedWidth = 0f
                        spinnerPaint.color = context.getColor(R.color.colorPrimary)
                        invalidate()
                    }

                    ButtonState.Loading -> {
                        buttonText = "Loading in progress"
                        spinnerPaint.color = context.getColor(R.color.colorAccent)
                        valueAnimator.apply {
                            setFloatValues(0.0f, widthSize.toFloat())
                            start()
                        }
                    }

                    ButtonState.Completed -> {
                        buttonText = "Download"
                        animatedWidth = 0f
                        spinnerPaint.color = context.getColor(R.color.colorPrimary)
                        invalidate()
                    }
                }
            }

    // define several paints for later drawing
    private var initialBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorPrimary)
    }
    private var animationPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorPrimaryDark)
    }
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.default_text_size)
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
    }
    private var spinnerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.colorPrimary)
        strokeWidth = SPINNER_STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRoundRect(
            0f,
            0f,
            widthSize.toFloat(),
            heightSize.toFloat(),
            BUTTON_CORNER_RADIUS,
            BUTTON_CORNER_RADIUS,
            initialBgPaint
        )

        canvas?.drawRoundRect(
            0f,
            0f,
            animatedWidth,
            heightSize.toFloat(),
            BUTTON_CORNER_RADIUS,
            BUTTON_CORNER_RADIUS,
            animationPaint
        )

        // draw button label centered on canvas
        canvas?.drawText(
            buttonText,
            widthSize.toFloat() / 2,
            heightSize.toFloat() / 2 + textOffset,
            textPaint
        )

        canvas?.drawArc(
            spinnerRect,
            SPINNER_START_ANGLE,
            animatedSweepAngle,
            false,
            spinnerPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h

        // pre-compute some geometric dimensions for later drawing
        textOffset = (textPaint.descent() - textPaint.ascent()) / 2 - textPaint.descent()

        val spinnerCenterX = 0.85f * widthSize
        val spinnerCenterY = heightSize / 2f
        spinnerRect.set(
            spinnerCenterX - SPINNER_RADIUS,
            spinnerCenterY - SPINNER_RADIUS,
            spinnerCenterX + SPINNER_RADIUS,
            spinnerCenterY + SPINNER_RADIUS
        )

        setMeasuredDimension(w, h)
    }
}

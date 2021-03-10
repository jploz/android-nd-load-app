package com.udacity.loadapp.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.udacity.loadapp.R
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private val cornerRadius = 16f
    private val spinnerRadius = 32f

    private val valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    private var buttonText = "Download"

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
        color = context.getColor(R.color.colorAccent)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRoundRect(
            0f,
            0f,
            widthSize.toFloat(),
            heightSize.toFloat(),
            cornerRadius,
            cornerRadius,
            initialBgPaint
        )

        // draw button label centered on canvas
        val textOffset = (textPaint.descent() - textPaint.ascent()) / 2 - textPaint.descent()
        canvas?.drawText(
            buttonText,
            widthSize.toFloat() / 2,
            heightSize.toFloat() / 2 + textOffset,
            textPaint
        )

        // TODO: move geometry calculations into `onMeasure`
        val spinnerCenterX = 0.85f * widthSize
        val spinnerCenterY = heightSize / 2f
        canvas?.drawArc(
            spinnerCenterX - spinnerRadius,
            spinnerCenterY - spinnerRadius,
            spinnerCenterX + spinnerRadius,
            spinnerCenterY + spinnerRadius,
            0f,
            width.toFloat(),
            true,
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
        setMeasuredDimension(w, h)
    }
}

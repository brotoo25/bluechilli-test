package com.abraaolima.bluechillitest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class CustomView(context: Context,
                 var shape: Shape) : View(context, null) {

    private var mPaint: Paint = Paint()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec),
                View.MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        mPaint.color = shape.color
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true

        when (shape.shapeType) {
            ShapeType.CIRCLE -> canvas.drawCircle(
                    width.toFloat() / 2.0f,
                    height.toFloat() / 2.0f,
                    height.toFloat() / 2.0f,
                    mPaint)
            ShapeType.SQUARE -> canvas.drawRect(
                    0f,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    mPaint)
        }
    }

    fun updateShape(shape: Shape) {
        this.shape = shape
        invalidate()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}
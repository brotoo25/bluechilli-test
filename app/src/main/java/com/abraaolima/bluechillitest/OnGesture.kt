package com.abraaolima.bluechillitest

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

abstract class OnGesture(context: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    internal abstract fun onSingleTouch(event: MotionEvent)

    internal abstract fun onDoubleTouch()

    internal abstract fun onSwipeLeft()

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
        private val TAG = "OnGesture"

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX <= 0) {
                        Log.i(TAG, "onSwipeLeft")
                        onSwipeLeft()
                    }
                    result = true
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return result
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.i(TAG, "onDoubleTouch")
            onDoubleTouch()
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            Log.i(TAG, "onSingleTouch")
            onSingleTouch(e)
            return super.onSingleTapConfirmed(e)
        }
    }
}
package org.senior_sigan.digits.views

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View

class TouchListener : View.OnTouchListener {
    private var mTmpPiont = PointF()
    private var mLastX: Float = 0f
    private var mLastY: Float = 0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val drawView = v as DrawView
        val action = event.action and MotionEvent.ACTION_MASK
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                processTouchDown(event, drawView)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                processTouchMove(event, drawView)
                true
            }
            MotionEvent.ACTION_UP -> {
                v.performClick()
                processTouchUp(drawView)
                true
            }
            else -> false
        }
    }

    private fun processTouchDown(event: MotionEvent, drawView: DrawView) {
        mLastX = event.x
        mLastY = event.y
        drawView.calcPos(mLastX, mLastY, mTmpPiont)
        val lastConvX = mTmpPiont.x
        val lastConvY = mTmpPiont.y
        drawView.model.startLine(lastConvX, lastConvY)
    }

    private fun processTouchMove(event: MotionEvent, drawView: DrawView) {
        val x = event.x
        val y = event.y

        drawView.calcPos(x, y, mTmpPiont)
        val newConvX = mTmpPiont.x
        val newConvY = mTmpPiont.y
        drawView.model.addLineElem(newConvX, newConvY)

        mLastX = x
        mLastY = y
        drawView.invalidate()
    }

    private fun processTouchUp(drawView: DrawView) {
        drawView.model.endLine()
    }
}
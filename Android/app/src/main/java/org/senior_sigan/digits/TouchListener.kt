package org.senior_sigan.digits

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import org.senior_sigan.digits.views.DrawModel
import org.senior_sigan.digits.views.DrawView

class TouchListener(
        private val drawView: DrawView,
        private val drawModel: DrawModel
) : View.OnTouchListener {
    private var mTmpPiont = PointF()
    private var mLastX: Float = 0f
    private var mLastY: Float = 0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val action = event.action and MotionEvent.ACTION_MASK
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                processTouchDown(event)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                processTouchMove(event)
                true
            }
            MotionEvent.ACTION_UP -> {
                v.performClick()
                processTouchUp()
                true
            }
            else -> false
        }
    }

    private fun processTouchDown(event: MotionEvent) {
        mLastX = event.x
        mLastY = event.y
        drawView.calcPos(mLastX, mLastY, mTmpPiont)
        val lastConvX = mTmpPiont.x
        val lastConvY = mTmpPiont.y
        drawModel.startLine(lastConvX, lastConvY)
    }

    private fun processTouchMove(event: MotionEvent) {
        val x = event.x
        val y = event.y

        drawView.calcPos(x, y, mTmpPiont)
        val newConvX = mTmpPiont.x
        val newConvY = mTmpPiont.y
        drawModel.addLineElem(newConvX, newConvY)

        mLastX = x
        mLastY = y
        drawView.invalidate()
    }

    private fun processTouchUp() {
        drawModel.endLine()
    }
}
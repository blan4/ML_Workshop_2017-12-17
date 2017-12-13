package org.senior_sigan.digits.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View


class DrawView(
        context: Context,
        attrs: AttributeSet?
) : View(context, attrs) {
    private var holder: DrawViewHolder = DrawViewHolder(this)

    val pixelData: FloatArray
        get() = holder.pixelData

    val model: DrawModel
        get() = holder.model

    init {
        setOnTouchListener(TouchListener())
    }

    /**
     * Clear screen
     */
    fun reset() {
        holder.reset()
    }

    fun calcPos(x: Float, y: Float, out: PointF) {
        holder.calcPos(x, y, out)
    }

    override fun onDraw(canvas: Canvas) {
        holder.onDraw(canvas)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}

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
    private var holder: DrawViewHolder? = null

    val pixelData: FloatArray?
        get() = holder?.pixelData

    var model: DrawModel? = null
        set(value) {
            field = value
            setup()
        }

    /**
     * Clear screen
     */
    fun reset() {
        holder?.reset()
    }

    fun calcPos(x: Float, y: Float, out: PointF) {
        holder?.calcPos(x, y, out)
    }

    override fun onDraw(canvas: Canvas) {
        holder?.onDraw(canvas)
    }

    /**
     * Configure new draw view
     */
    fun setup() {
        val m = model ?: return
        release()
        holder = DrawViewHolder(m, this)
    }

    /**
     * Destroys everything.
     */
    fun release() {
        holder?.reset()
        holder?.release()
        holder = null
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}

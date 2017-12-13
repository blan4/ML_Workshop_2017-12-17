package org.senior_sigan.digits.views

import android.graphics.*
import android.view.View
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

/**
 * DrawView with all the fields are not null.
 * Special holder to do all stuff safe.
 * If you need to destroy this view, just release it and make null.
 */
class DrawViewHolder(
        private val mModel: DrawModel,
        private val view: View
) {
    private val mOffscreenBitmap: Bitmap = createBitmap()
    private val mOffscreenCanvas: Canvas = Canvas(mOffscreenBitmap)
    private var mDrawnLineSize = 0
    private val mPaint = Paint()
    private val mMatrix = Matrix()
    private val mInvMatrix = Matrix()

    private val mTmpPoints = FloatArray(2)

    private var isSetup = false
    private val setupLock: ReadWriteLock = ReentrantReadWriteLock()

    init {
        reset()
    }

    val pixelData: FloatArray
        get() {
            val width = mOffscreenBitmap.width
            val height = mOffscreenBitmap.height
            val pixels = IntArray(width * height)
            mOffscreenBitmap.getPixels(pixels, 0, width, 0, 0, width, height)

            val retPixels = FloatArray(pixels.size)
            for (i in pixels.indices) {
                val pix = pixels[i]
                val b = pix and 0xff
                retPixels[i] = ((0xff - b) / 255.0).toFloat()
            }
            return retPixels
        }

    fun reset() {
        mDrawnLineSize = 0

        mPaint.color = Color.WHITE
        val width = mOffscreenBitmap.width
        val height = mOffscreenBitmap.height
        mOffscreenCanvas.drawRect(Rect(0, 0, width, height), mPaint)
    }

    fun onDraw(canvas: Canvas) {
        setup()
        val startIndex = if (mDrawnLineSize < 1) {
            0
        } else {
            mDrawnLineSize - 1
        }

        DrawRenderer.renderModel(mOffscreenCanvas, mModel, mPaint, startIndex)
        canvas.drawBitmap(mOffscreenBitmap, mMatrix, mPaint)

        mDrawnLineSize = mModel.lineSize
    }

    fun release() {
        mOffscreenBitmap.recycle()
    }

    private fun createBitmap(): Bitmap {
        return Bitmap.createBitmap(mModel.width, mModel.height, Bitmap.Config.ARGB_8888)
    }

    /**
     * Convert screen position to local pos (pos in bitmap)
     */
    fun calcPos(x: Float, y: Float, out: PointF) {
        mTmpPoints[0] = x
        mTmpPoints[1] = y
        mInvMatrix.mapPoints(mTmpPoints)
        out.x = mTmpPoints[0]
        out.y = mTmpPoints[1]
    }

    private fun setup() {
        if (isSetup) return
        setupLock.writeLock().withLock {
            safeSetup()
        }
    }

    private fun safeSetup() {
        if (isSetup) return

        // View size
        val width = view.width.toFloat()
        val height = view.height.toFloat()

        // Model (bitmap) size
        val modelWidth = mModel.width.toFloat()
        val modelHeight = mModel.height.toFloat()

        val scaleW = width / modelWidth
        val scaleH = height / modelHeight

        var scale = scaleW
        if (scale > scaleH) {
            scale = scaleH
        }

        val newCx = modelWidth * scale / 2
        val newCy = modelHeight * scale / 2
        val dx = width / 2 - newCx
        val dy = height / 2 - newCy

        mMatrix.setScale(scale, scale)
        mMatrix.postTranslate(dx, dy)
        mMatrix.invert(mInvMatrix)

        isSetup = true
    }
}
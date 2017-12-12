package org.senior_sigan.digits

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.find
import org.senior_sigan.digits.views.DrawModel
import org.senior_sigan.digits.views.DrawView


class MainActivity : AppCompatActivity() {
    private lateinit var drawView: DrawView
    private val PIXEL_WIDTH = 28
    private val drawModel = DrawModel(PIXEL_WIDTH, PIXEL_WIDTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawView = find(R.id.draw)
        drawView.setModel(drawModel)
        drawView.setOnTouchListener(TouchListener(drawView, drawModel))

        val resText: TextView = find(R.id.tfRes)

        find<Button>(R.id.btn_clear).setOnClickListener {
            drawModel.clear()
            drawView.reset()
            drawView.invalidate()
        }

        find<Button>(R.id.btn_class).setOnClickListener {
            val pixels = drawView.pixelData
            resText.text = "???"
        }
    }

    override fun onResume() {
        drawView.onResume()
        super.onResume()
    }

    override fun onPause() {
        drawView.onPause()
        super.onPause()
    }
}
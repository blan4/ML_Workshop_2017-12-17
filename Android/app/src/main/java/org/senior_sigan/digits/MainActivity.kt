package org.senior_sigan.digits

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.find
import org.senior_sigan.digits.views.DrawView


class MainActivity : AppCompatActivity() {
    private lateinit var drawView: DrawView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawView = find(R.id.draw)

        val resText: TextView = find(R.id.tfRes)

        find<Button>(R.id.btn_clear).setOnClickListener {
            drawView.reset()
            drawView.invalidate()
        }

        find<Button>(R.id.btn_class).setOnClickListener {
            val pixels = drawView.pixelData
            resText.text = "???"
        }
    }
}
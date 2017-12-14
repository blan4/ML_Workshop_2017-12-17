package org.senior_sigan.digits

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.senior_sigan.digits.ml.IClassifier
import org.senior_sigan.digits.views.DrawView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private val TAG = "MNIST"
    private lateinit var drawView: DrawView

    private val PIXEL_WIDTH = 28L

    private val classifiers = mutableListOf<IClassifier>()

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
            thread {
                val text = StringBuilder()
                val pixels = drawView.pixelData
                classifiers.forEach {
                    val pred = it.predict(pixels)
                    text.append("${it.name}: ${pred.label} ${pred.proba}")
                }
                runOnUiThread {
                    resText.text = text.toString()
                }
            }
        }

        loadModels()
    }

    private fun loadModels() {
        loadModel("opt_mnist_convnet")
    }

    private fun loadModel(name: String) {
        thread {
            try {
                // TODO: add more classifiers
                classifiers.add(DigitsClassifier(
                        assets, "$name.pb", PIXEL_WIDTH,
                        "conv2d_1_input", "dense_2/Softmax",
                        name))
                runOnUiThread { toast("$name.pb loaded") }
            } catch (e: Exception) {
                Log.e(TAG, "Can't load $name model", e)
                runOnUiThread { toast("Can't load $name model") }
            }
        }
    }
}
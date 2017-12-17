package org.senior_sigan.digits

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.senior_sigan.digits.ml.IClassifier
import org.senior_sigan.digits.ml.models.DigitsClassifierFlatten
import org.senior_sigan.digits.ml.models.DigitsClassifierSquare
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
                    try {
                        val pred = it.predict(pixels)
                        text.append("${it.name}: ${pred.label} ${pred.proba}\n")
                    } catch (e: Exception) {
                        text.append("${it.name}: err\n")
                        Log.e(TAG, "Can't predict ${it.name}", e)
                    }
                }
                runOnUiThread {
                    resText.text = text.toString()
                }
            }
        }

        loadModels()
    }

    private fun loadModels() {
        thread {
            loadModel("dnn", {
                DigitsClassifierFlatten(assets,
                        "dnn.pb",
                        PIXEL_WIDTH,
                        "dense_1_input",
                        "dense_2/Softmax",
                        "dnn")
            })

            loadModel("conv", {
                DigitsClassifierSquare(assets,
                        "conv.pb",
                        PIXEL_WIDTH,
                        "conv2d_1_input",
                        "dense_2/Softmax",
                        "conv")
            })
        }
    }

    private fun loadModel(name: String, builder: () -> IClassifier) {
        try {
            classifiers.add(builder())
            runOnUiThread { toast("$name.pb loaded") }
        } catch (e: Exception) {
            Log.e(TAG, "Can't load $name.pb model", e)
            runOnUiThread { toast("Can't load $name.pb model") }
        }
    }
}
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

    private val PIXEL_WIDTH = 28L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // R.layout.activity_main is configured

        // find DrawView

        // find text view

        // find buttons

        // add button reactions

        // do predictions by getting `drawView.pixelData`

        // put text results in text view

        // load models
    }

    private fun loadModels() {
        // in another thread load models by instantiating
        // DigitsClassifierSquare for Conv net
        // DigitsClassifierFlatten for DNN
    }

    private fun loadModel(name: String, builder: () -> IClassifier) {
        // recommendation
        // load model with try catch blocks and so on
        // show toasts or errors
    }
}
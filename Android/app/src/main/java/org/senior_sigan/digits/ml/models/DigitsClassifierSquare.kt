package org.senior_sigan.digits.ml.models

import android.content.res.AssetManager
import android.util.Log
import org.senior_sigan.digits.ml.IClassifier
import org.senior_sigan.digits.ml.Prediction
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

/**
 * Conv net loader and classifier with 2d input
 */
class DigitsClassifierSquare(
        assetManager: AssetManager,
        modelPath: String,
        private val inputSize: Long,
        private val inputName: String,
        private val outputName: String,
        override val name: String
) : IClassifier {
    private val TAG = "DigitsClassifierSquare $name"
    private var tfHelper = TensorFlowInferenceInterface(assetManager, modelPath)
    private var outputNames = arrayOf(outputName)
    override val numClasses: Int = 10
    override val labels: List<String> = 0.rangeTo(numClasses).map { it.toString() }
    private var output = FloatArray(numClasses)

    override fun predict(pixels: FloatArray): Prediction {
        tfHelper.feed(inputName, pixels, 1L, inputSize, inputSize, 1L)

        tfHelper.run(outputNames)
        tfHelper.fetch(outputName, output)

        val preds = output.indices.map { i ->Prediction(output[i], labels[i]) }
        Log.i(TAG, "Preds: $preds")

        return preds.sortedByDescending { it.probaF }.first()
    }
}
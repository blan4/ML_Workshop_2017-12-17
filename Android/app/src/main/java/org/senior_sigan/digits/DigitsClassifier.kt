package org.senior_sigan.digits

import android.content.res.AssetManager
import android.util.Log
import org.senior_sigan.digits.ml.IClassifier
import org.senior_sigan.digits.ml.Prediction
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

class DigitsClassifier(
        assetManager: AssetManager,
        modelPath: String,
        private val inputSize: Long,
        private val inputName: String,
        private val outputName: String,
        override val name: String
) : IClassifier {
    private val TAG = "DigitsClassifier $name"
    private var tfHelper = TensorFlowInferenceInterface(assetManager, modelPath)
    private var outputNames = arrayOf(outputName)
    override val numClasses: Int = 10
    override val labels: List<String> = 0.rangeTo(numClasses).map { it.toString() }
    private var output = FloatArray(numClasses)

    override fun predict(pixels: FloatArray): Prediction {
        tfHelper.feed(inputName, pixels, 1L, inputSize, inputSize, 1L)
        tfHelper.feed("keep_prob", floatArrayOf(1f))

        tfHelper.run(outputNames)
        tfHelper.fetch(outputName, output)

        val pred = Prediction()
        for (i in output.indices) {
            Log.d(TAG, "Out: ${output[i]}, Label: ${labels[i]}")
        }

        return pred
    }
}
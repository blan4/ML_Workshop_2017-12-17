package org.senior_sigan.digits.ml

interface IClassifier {
    val name: String
    val numClasses: Int
    val labels: List<String>
    fun predict(pixels: FloatArray): Prediction
}
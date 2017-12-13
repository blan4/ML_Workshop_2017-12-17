package org.senior_sigan.digits.ml

data class Prediction(
        val probaF: Float = -0f,
        val label: String = ""
) {
    val proba: String
        get() = "%.2f".format(probaF)
}
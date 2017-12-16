package org.senior_sigan.digits

import android.content.res.AssetManager
import org.senior_sigan.digits.ml.IClassifier

inline fun <reified T : IClassifier> buildModel(
        assetManager: AssetManager,
        modelPath: String,
        inputSize: Long,
        inputName: String,
        outputName: String,
        name: String
): T {
    val constructor = T::class.java.declaredConstructors.first()
    return constructor.newInstance(assetManager, modelPath, inputSize, inputName, outputName, name) as T
}
package org.senior_sigan.digits.views

/*
   Copyright 2016 Narrative Nights Inc. All Rights Reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   https://raw.githubusercontent.com/miyosuda/TensorFlowAndroidMNIST/master/app/src/main/java/jp/narr/tensorflowmnist/DrawRenderer.java
*/

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * Changed by ilya siganov on 12/12/17.
 * https://raw.githubusercontent.com/miyosuda/TensorFlowAndroidMNIST/master/app/src/main/java/jp/narr/tensorflowmnist/DrawRenderer.java
 */

object DrawRenderer {
    /**
     * Draw lines to canvas
     */
    fun renderModel(
            canvas: Canvas,
            model: DrawModel,
            paint: Paint,
            startLineIndex: Int
    ) {
        paint.isAntiAlias = true
        paint.color = Color.BLACK

        for (i in startLineIndex until model.lineSize) {
            val line = model.getLine(i)
            if (line.elemSize < 1) {
                continue
            }

            for (j in 1 until line.elemSize) {
                val le = line.getElem(j - 1)
                val e = line.getElem(j)
                canvas.drawLine(le.x, le.y, e.x, e.y, paint)
            }
        }
    }
}

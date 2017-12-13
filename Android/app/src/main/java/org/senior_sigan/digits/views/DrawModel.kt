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
*/


/**
 * Changed by ilya siganov on 12/12/17.
 */
class DrawModel(
        val width: Int,
        val height: Int
) {
    private val mCurrentLine = Line()
    private val mLines = mutableListOf<Line>()

    val lineSize: Int
        get() = mLines.size

    fun startLine(x: Float, y: Float) {
        mCurrentLine.addElem(LineElem(x, y))
        mLines.add(mCurrentLine)
    }

    fun endLine() {
        mCurrentLine.reset()
    }

    fun addLineElem(x: Float, y: Float) {
        mCurrentLine.addElem(LineElem(x, y))
    }

    fun getLine(index: Int): Line {
        return mLines[index]
    }

    fun clear() {
        mLines.clear()
    }

    inner class LineElem(var x: Float, var y: Float)

    inner class Line {
        private val elems = mutableListOf<LineElem>()

        val elemSize: Int
            get() = elems.size

        fun addElem(elem: LineElem) {
            elems.add(elem)
        }

        fun getElem(index: Int): LineElem {
            return elems[index]
        }

        fun reset() {
            elems.clear()
        }
    }
}
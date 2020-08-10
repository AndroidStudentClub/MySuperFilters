package ru.androidschool.mysuperfilters.filters

import android.graphics.Bitmap
import android.graphics.Color

class ConvolutionMatrix(size: Int) {
    var Matrix: Array<DoubleArray>
    var Factor = 1.0
    var Offset = 1.0
    fun setAll(value: Double) {
        for (x in 0 until SIZE) {
            for (y in 0 until SIZE) {
                Matrix[x][y] = value
            }
        }
    }

    fun applyConfig(config: Array<DoubleArray>) {
        for (x in 0 until SIZE) {
            for (y in 0 until SIZE) {
                Matrix[x][y] = config[x][y]
            }
        }
    }

    companion object {
        const val SIZE = 3
        fun computeConvolution3x3(src: Bitmap?, matrix: ConvolutionMatrix): Bitmap {
            var src = src
            val width = src!!.width
            val height = src.height
            val result = Bitmap.createBitmap(width, height, src.config)
            var A: Int
            var R: Int
            var G: Int
            var B: Int
            var sumR: Int
            var sumG: Int
            var sumB: Int
            val pixels = Array(
                SIZE
            ) { IntArray(SIZE) }
            for (y in 0 until height - 2) {
                for (x in 0 until width - 2) {

                    // get pixel matrix
                    for (i in 0 until SIZE) {
                        for (j in 0 until SIZE) {
                            pixels[i][j] = src.getPixel(x + i, y + j)
                        }
                    }

                    // get alpha of center pixel
                    A = Color.alpha(pixels[1][1])

                    // init color sum
                    sumB = 0
                    sumG = sumB
                    sumR = sumG

                    // get sum of RGB on matrix
                    for (i in 0 until SIZE) {
                        for (j in 0 until SIZE) {
                            sumR += Color.red(pixels[i][j]) * matrix.Matrix[i][j].toInt()
                            sumG += Color.green(pixels[i][j]) * matrix.Matrix[i][j].toInt()
                            sumB += Color.blue(pixels[i][j]) * matrix.Matrix[i][j].toInt()
                        }
                    }

                    // get final Red
                    R = (sumR / matrix.Factor + matrix.Offset).toInt()
                    if (R < 0) {
                        R = 0
                    } else if (R > 255) {
                        R = 255
                    }

                    // get final Green
                    G = (sumG / matrix.Factor + matrix.Offset).toInt()
                    if (G < 0) {
                        G = 0
                    } else if (G > 255) {
                        G = 255
                    }

                    // get final Blue
                    B = (sumB / matrix.Factor + matrix.Offset).toInt()
                    if (B < 0) {
                        B = 0
                    } else if (B > 255) {
                        B = 255
                    }

                    // apply new pixel
                    result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B))
                }
            }
            src.recycle()
            src = null

            // final image
            return result
        }
    }

    init {
        Matrix = Array(size) { DoubleArray(size) }
    }
}
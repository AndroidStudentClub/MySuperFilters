package ru.androidschool.mysuperfilters.filters

import android.graphics.Bitmap
import android.graphics.Color
import ru.androidschool.mysuperfilters.MySuperFilter


class Sketch : MySuperFilter {

    override fun apply(src: Bitmap): Bitmap? {
        var src = src
        val type = 6
        val threshold = 130
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
        val pixels = Array(3) { IntArray(3) }
        for (y in 0 until height - 2) {
            for (x in 0 until width - 2) {
                //      get pixel matrix
                for (i in 0..2) {
                    for (j in 0..2) {
                        pixels[i][j] = src.getPixel(x + i, y + j)
                    }
                }
                // get alpha of center pixel
                A = Color.alpha(pixels[1][1])
                // init color sum
                sumB = 0
                sumG = sumB
                sumR = sumG
                sumR = type * Color.red(pixels[1][1]) - Color.red(
                    pixels[0][0]
                ) - Color.red(pixels[0][2]) - Color.red(
                    pixels[2][0]
                ) - Color.red(pixels[2][2])
                sumG = type * Color.green(pixels[1][1]) - Color.green(
                    pixels[0][0]
                ) - Color.green(pixels[0][2]) - Color.green(pixels[2][0]) - Color.green(
                    pixels[2][2]
                )
                sumB = type * Color.blue(pixels[1][1]) - Color.blue(
                    pixels[0][0]
                ) - Color.blue(pixels[0][2]) - Color.blue(pixels[2][0]) - Color.blue(
                    pixels[2][2]
                )
                // get final Red
                R = (sumR + threshold)
                if (R < 0) {
                    R = 0
                } else if (R > 255) {
                    R = 255
                }
                // get final Green
                G = (sumG + threshold)
                if (G < 0) {
                    G = 0
                } else if (G > 255) {
                    G = 255
                }
                // get final Blue
                B = (sumB + threshold)
                if (B < 0) {
                    B = 0
                } else if (B > 255) {
                    B = 255
                }
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B))
            }
        }
        src.recycle()
        return result
    }
}
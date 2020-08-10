package ru.androidschool.mysuperfilters.filters

import android.graphics.Bitmap
import android.graphics.Color
import ru.androidschool.mysuperfilters.MySuperFilter


class Invert : MySuperFilter {

    override fun apply(src: Bitmap): Bitmap? {
        val output =
            Bitmap.createBitmap(src.width, src.height, src.config)
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixelColor: Int
        val height: Int = src.height
        val width: Int = src.width

        for (y in 0 until height) {
            for (x in 0 until width) {
                pixelColor = src.getPixel(x, y)
                A = Color.alpha(pixelColor)
                R = 255 - Color.red(pixelColor)
                G = 255 - Color.green(pixelColor)
                B = 255 - Color.blue(pixelColor)
                output.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        src.recycle()

        return output
    }
}
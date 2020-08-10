package ru.androidschool.mysuperfilters.filters

import android.graphics.*
import ru.androidschool.mysuperfilters.MySuperFilter


class GrayScale : MySuperFilter {

    override fun apply(src: Bitmap): Bitmap? {
        //Array to generate Gray-Scale image
        //Array to generate Gray-Scale image
        val GrayArray = floatArrayOf(
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )

        val colorMatrixGray = ColorMatrix(GrayArray)

        val w: Int = src.width
        val h: Int = src.height

        val bitmapResult = Bitmap
            .createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvasResult = Canvas(bitmapResult)
        val paint = Paint()

        val filter = ColorMatrixColorFilter(colorMatrixGray)
        paint.colorFilter = filter
        canvasResult.drawBitmap(src, 0.0f, 0.0f, paint)

        src.recycle()

        return bitmapResult
    }
}
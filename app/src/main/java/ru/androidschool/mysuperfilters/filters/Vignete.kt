package ru.androidschool.mysuperfilters.filters

import android.graphics.*
import ru.androidschool.mysuperfilters.MySuperFilter


class Vignete : MySuperFilter {

    override fun apply(src: Bitmap): Bitmap? {
        val width: Int = src.width
        val height: Int = src.height

        val radius = (width / 1.2).toFloat()
        val colors = intArrayOf(0, 0x55000000, -0x1000000)
        val positions = floatArrayOf(0.0f, 0.5f, 1.0f)

        val gradient =
            RadialGradient(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                radius,
                colors,
                positions,
                Shader.TileMode.CLAMP
            )

        //RadialGradient gradient = new RadialGradient(width / 2, height / 2, radius, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);


        //RadialGradient gradient = new RadialGradient(width / 2, height / 2, radius, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);
        val options = BitmapFactory.Options()
        options.inScaled = false
        options.inMutable = true

        val bitmap: Bitmap = src.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(bitmap)
        canvas.drawARGB(1, 0, 0, 0)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.shader = gradient

        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectf = RectF(rect)

        canvas.drawRect(rectf, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return bitmap
    }
}
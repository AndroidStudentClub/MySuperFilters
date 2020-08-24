package ru.androidschool.mysuperfilters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object ImageLoader {

   suspend fun getOriginalBitmapAsync(url: String): Bitmap =
        withContext(Dispatchers.IO) {
            URL(url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }
}
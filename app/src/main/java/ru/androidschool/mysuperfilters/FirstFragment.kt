package ru.androidschool.mysuperfilters

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.*
import ru.androidschool.mysuperfilters.filters.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), ThumbnailsAdapter.ThumbnailsAdapterListener {


    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("FirstFragment", "$exception handled!")
    }

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + handler
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onFilterSelected(filter: MySuperFilter?, url: String) {
        coroutineScope.launch(Dispatchers.Main) {
            progress_bar.visibility = View.VISIBLE
            val originalBitmap = ImageLoader.getOriginalBitmapAsync(url)
            // 1.Проверка на наличие фильтра
            if (filter != null) {
                // 2. Применение фильтра
                val snowFilterBitmap = applyFilterAsync(originalBitmap, filter)
                // 3. Загрузка полученного Bitmap
                loadImage(snowFilterBitmap)
            } else {
                // 4. Если фильтра не было - то загружаем оригинальное изображение
                loadImage(originalBitmap)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        coroutineScope.launch(Dispatchers.Main) {
            val originalBitmap =
                ImageLoader.getOriginalBitmapAsync("https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg")
            loadImage(originalBitmap)
        }

        recycler_view.adapter = ThumbnailsAdapter(
            listOf(
                FilterItem(
                    previewUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    imageUrl = "https://image.tmdb.org/t/p/w342/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    filterName = "Normal",
                    filter = null
                ),
                FilterItem(
                    previewUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    imageUrl = "https://image.tmdb.org/t/p/w342/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    filterName = "Blur",
                    filter = Blur()
                ),
                FilterItem(
                    previewUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    imageUrl = "https://image.tmdb.org/t/p/w342/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    filterName = "Sketch",
                    filter = Sketch()
                ),
                FilterItem(
                    previewUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    imageUrl = "https://image.tmdb.org/t/p/w342/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    filterName = "GrayScale",
                    filter = GrayScale()
                ),
                FilterItem(
                    previewUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    imageUrl = "https://image.tmdb.org/t/p/w342/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    filterName = "Invert",
                    filter = Invert()
                ),

                FilterItem(
                    previewUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    imageUrl = "https://image.tmdb.org/t/p/w342/aMpyrCizvSdc0UIMblJ1srVgAEF.jpg",
                    filterName = "Vignete",
                    filter = Vignete()
                )

            ), this
        )
    }

    private suspend fun applyFilterAsync(
        originalBitmap: Bitmap,
        filter: MySuperFilter? = Blur()
    ): Bitmap =
        withContext(Dispatchers.Default) {
            filter?.apply(originalBitmap)!!
        }

    private fun loadImage(source: Bitmap) {
        progress_bar.visibility = View.GONE
        place_holder_imageview?.setImageBitmap(source)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
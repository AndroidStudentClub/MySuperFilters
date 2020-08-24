package ru.androidschool.mysuperfilters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ThumbnailsAdapter(
    private val thumbnailItemList: List<FilterItem>,
    private val listener: ThumbnailsAdapterListener
) :
    RecyclerView.Adapter<ThumbnailsAdapter.MyViewHolder>() {
    private var selectedIndex = 0
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var image: ImageView = v.findViewById(R.id.thumbnail)
        internal var filterName: TextView = v.findViewById(R.id.filter_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filter_preview, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val thumbnailItem: FilterItem = thumbnailItemList[position]
        thumbnailItem.imageUrl?.let {
            coroutineScope.launch(Dispatchers.Main) {
                holder.image.setImageBitmap(ImageLoader.getOriginalBitmapAsync(it))
            }
        }

        holder.image.setOnClickListener {
            listener.onFilterSelected(thumbnailItem.filter, thumbnailItem.imageUrl.orEmpty())
            selectedIndex = position
            notifyDataSetChanged()

        }
        holder.filterName.text = thumbnailItem.filterName
        if (selectedIndex == position) {
            holder.filterName.setTextColor(
                ContextCompat.getColor(
                    holder.filterName.context,
                    R.color.filter_label_selected
                )
            )
        } else {
            holder.filterName.setTextColor(
                ContextCompat.getColor(
                    holder.filterName.context,
                    R.color.filter_label_normal
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return thumbnailItemList.size
    }

    interface ThumbnailsAdapterListener {
        fun onFilterSelected(filter: MySuperFilter?, url: String)
    }
}
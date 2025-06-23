package com.example.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.model.ImageItem

class StaggeredImageAdapter(
    private val context: Context,
    private var imageList: MutableList<ImageItem>
) : RecyclerView.Adapter<StaggeredImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = imageList[position]
        Glide.with(context)
            .load(imageItem.imageUrl)
            .placeholder(R.drawable.placeholder)  // 你需要在 drawable 文件夹加一个占位图 placeholder.png
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    // 数据更新 - 下拉刷新用
    fun setNewData(newData: List<ImageItem>) {
        imageList.clear()
        imageList.addAll(newData)
        notifyDataSetChanged()
    }

    // 数据追加 - 上滑加载用
    fun addData(moreData: List<ImageItem>) {
        val startPosition = imageList.size
        imageList.addAll(moreData)
        notifyItemRangeInserted(startPosition, moreData.size)
    }
}

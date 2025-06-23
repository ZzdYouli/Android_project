package com.example.demo.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.demo.R

object ImageLoaderUtil {

    /**
     * 普通加载图片
     */
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)    // 占位图
            .error(R.drawable.placeholder)          // 加载失败显示
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    /**
     * 加载圆角图片
     */
    fun loadRoundedImage(context: Context, url: String, imageView: ImageView, cornerRadius: Int = 16) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    /**
     * 可扩展其他图片特效：如模糊、灰度、圆形等
     * 示例：需要用 Glide Transformation 库
     */
}

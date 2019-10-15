package com.movies.popular.common.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.movies.popular.di.glide.module.GlideApp

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = [
        "android:bind_img_uri",
        "android:bind_img_placeholder",
        "android:bind_img_fallback"
    ], requireAll = false)
    fun loadImage(imageView: ImageView, uri: String?, placeHolder: Drawable?, fallback: Drawable?) {
        GlideApp
                .with(imageView.context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .dontAnimate()
                .placeholder(placeHolder)
                .fallback(fallback)
                .into(imageView)


    }


}
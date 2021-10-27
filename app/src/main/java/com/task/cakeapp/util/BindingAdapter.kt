package com.task.cakeapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.task.cakeapp.R

//To add custom attribute to ImageView and set data from image url
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        //using glide library to set image from url
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.ic_cake)
            .into(view)
    }else{
        //If image url is null
        view.setImageResource(R.drawable.ic_cake)
    }
}
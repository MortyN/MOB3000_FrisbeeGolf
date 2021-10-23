package com.example.mob3000_frisbeegolf.model


import android.widget.ImageView
import com.bumptech.glide.Glide

import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.target.Target

@BindingAdapter("profileImage")
fun ImageView.load(imageAddress: String) {
    Glide.with(this)
        .load(imageAddress)
        .override(Target.SIZE_ORIGINAL, 200)
        .into(this)
}

data class FeedPost(var message: String, var userName: String, var userProfileImg: String)
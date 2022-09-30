package com.ianpedraza.masterrecylerview.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ianpedraza.masterrecylerview.R

class ViewExtensions {

    companion object {
        fun ImageView.loadImageByUrl(
            urlImage: String
        ) = Glide.with(this)
            .load(urlImage)
            .centerCrop()
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .into(this)
    }
}

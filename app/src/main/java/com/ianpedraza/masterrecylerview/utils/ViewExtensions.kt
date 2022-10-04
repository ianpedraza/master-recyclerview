package com.ianpedraza.masterrecylerview.utils

import android.graphics.Paint
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ianpedraza.masterrecylerview.R

class ViewExtensions {

    companion object {
        fun ImageView.loadImageByUrl(
            urlImage: String
        ) = Glide.with(this)
            .load(urlImage)
            .centerCrop()
            .fitCenter()
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .into(this)

        fun ImageView.loadCircleImageByUrl(
            urlImage: String
        ) = Glide.with(this)
            .load(urlImage)
            .centerCrop()
            .fitCenter()
            .circleCrop()
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .into(this)

        fun View.goneIf(value: Boolean) {
            visibility = if (value) {
                GONE
            } else {
                VISIBLE
            }
        }

        fun View.invisibleIf(value: Boolean) {
            visibility = if (value) {
                INVISIBLE
            } else {
                VISIBLE
            }
        }

        fun TextView.strikeThru(value: Boolean) {
            paintFlags = if (value) {
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}

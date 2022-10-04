package com.ianpedraza.masterrecylerview.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ianpedraza.masterrecylerview.R
import java.util.*

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

        fun SwipeRefreshLayout.refresh(onRefresh: () -> Unit) {
            isRefreshing = true
            onRefresh()
            isRefreshing = false
        }

        fun ImageView.loadImageByUrl(
            urlImage: String,
            onLoad: ((Drawable?) -> Unit)? = null
        ) = Glide.with(this)
            .load(urlImage)
            .centerCrop()
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ) = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoad?.let { onLoad(resource) }
                    return false
                }
            })
            .into(this)

        fun Int.toPokedexNumber() = String.format("%04d", this)

        fun String.capitalizeFirst() =
            this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        fun CardView.setCardBackgroundDominantColorFrom(bitmap: Bitmap) =
            Palette.Builder(bitmap).generate { palette ->
                val dominantColor = palette?.getDominantColor(Color.BLACK)

                dominantColor?.let {
                    val isColorDark = ColorsManager.isColorDark(dominantColor)

                    val color = if (isColorDark) {
                        ColorsManager.lightenColor(dominantColor, 0.25f)
                    } else {
                        dominantColor
                    }

                    this.setCardBackgroundColor(color)
                }
            }
    }
}

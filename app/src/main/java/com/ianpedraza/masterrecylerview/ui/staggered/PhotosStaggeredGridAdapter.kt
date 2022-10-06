package com.ianpedraza.masterrecylerview.ui.staggered

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.photos.Photo
import com.ianpedraza.masterrecylerview.databinding.ItemStaggeredGridPhotosBinding
import com.ianpedraza.masterrecylerview.ui.grid.PhotosAction
import com.ianpedraza.masterrecylerview.ui.grid.PhotosDiffCallback
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadImageByUrl

class PhotosStaggeredGridAdapter(
    private val onAction: (PhotosAction) -> Unit
) : ListAdapter<Photo, PhotosStaggeredGridAdapter.ViewHolder>(PhotosDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(getItem(position), onAction)

    class ViewHolder private constructor(
        private val binding: ItemStaggeredGridPhotosBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStaggeredGridPhotosBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Photo, onAction: (PhotosAction) -> Unit) {
            with(binding) {
                root.setOnClickListener { onAction(PhotosAction.OnClick(item)) }
                imageViewStaggeredGrid.loadImageByUrl(item.image)
            }
        }
    }
}

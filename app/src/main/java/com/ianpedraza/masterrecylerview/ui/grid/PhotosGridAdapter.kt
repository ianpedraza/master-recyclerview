package com.ianpedraza.masterrecylerview.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.photos.Photo
import com.ianpedraza.masterrecylerview.databinding.ItemGridPhotosBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadImageByUrl

class PhotosGridAdapter(
    private val onAction: (PhotosAction) -> Unit
) : ListAdapter<Photo, PhotosGridAdapter.ViewHolder>(PhotosDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), onAction)
    }

    class ViewHolder private constructor(
        private val binding: ItemGridPhotosBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGridPhotosBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Photo, onAction: (PhotosAction) -> Unit) = with(binding) {
            root.setOnClickListener { onAction(PhotosAction.OnClick(item)) }
            imageViewPhotoGrid.loadImageByUrl(item.image)
        }
    }
}

object PhotosDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Photo,
        newItem: Photo
    ): Boolean = oldItem == newItem
}

sealed interface PhotosAction {
    data class OnClick(val photo: Photo) : PhotosAction
}

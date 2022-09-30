package com.ianpedraza.masterrecylerview.ui.linear

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.travels.Ad
import com.ianpedraza.masterrecylerview.data.travels.TravelsCover
import com.ianpedraza.masterrecylerview.data.travels.TravelsDescription
import com.ianpedraza.masterrecylerview.databinding.ItemAdBinding
import com.ianpedraza.masterrecylerview.databinding.ItemTravelsCoverBinding
import com.ianpedraza.masterrecylerview.databinding.ItemTravelsDescriptionBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadImageByUrl
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_COVER = 0
private const val ITEM_VIEW_TYPE_DESCRIPTION = 1
private const val ITEM_VIEW_TYPE_AD = 2

class TravelsAdapter :
    ListAdapter<TravelsDataItem, RecyclerView.ViewHolder>(TravelsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_VIEW_TYPE_COVER -> CoverViewHolder.from(parent)
        ITEM_VIEW_TYPE_DESCRIPTION -> DescriptionViewHolder.from(parent)
        ITEM_VIEW_TYPE_AD -> AdViewHolder.from(parent)
        else -> throw ClassCastException("Unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoverViewHolder -> {
                val coverItem = getItem(position) as TravelsDataItem.CoverItem
                holder.bind(coverItem.cover)
            }
            is DescriptionViewHolder -> {
                val descriptionItem = getItem(position) as TravelsDataItem.DescriptionItem
                holder.bind(descriptionItem.description)
            }
            is AdViewHolder -> {
                val adItem = getItem(position) as TravelsDataItem.AdItem
                holder.bind(adItem.ad)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TravelsDataItem.CoverItem -> ITEM_VIEW_TYPE_COVER
            is TravelsDataItem.DescriptionItem -> ITEM_VIEW_TYPE_DESCRIPTION
            is TravelsDataItem.AdItem -> ITEM_VIEW_TYPE_AD
        }
    }

    class CoverViewHolder(
        private val binding: ItemTravelsCoverBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CoverViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTravelsCoverBinding.inflate(layoutInflater, parent, false)
                return CoverViewHolder(binding)
            }
        }

        fun bind(item: TravelsCover) = with(binding) {
            textViewItemTravelsCoverTitle.text = item.title
            textViewItemTravelsCoverHeadline.text = item.headline
            imageViewItemTravelsCover.loadImageByUrl(item.image)
        }
    }

    class DescriptionViewHolder(
        private val binding: ItemTravelsDescriptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DescriptionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTravelsDescriptionBinding.inflate(layoutInflater, parent, false)
                return DescriptionViewHolder(binding)
            }
        }

        fun bind(item: TravelsDescription) = with(binding) {
            textViewItemTravelsDescriptionTitle.text = item.title
            textViewItemTravelsDescriptionBody.text = item.body
        }
    }

    class AdViewHolder(
        private val binding: ItemAdBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): AdViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAdBinding.inflate(layoutInflater, parent, false)
                return AdViewHolder(binding)
            }
        }

        fun bind(item: Ad) = with(binding) {
            textViewItemAdTitle.text = item.title
            textViewItemAdBody.text = item.body
        }
    }
}

private object TravelsDiffCallback : DiffUtil.ItemCallback<TravelsDataItem>() {
    override fun areItemsTheSame(oldItem: TravelsDataItem, newItem: TravelsDataItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TravelsDataItem, newItem: TravelsDataItem): Boolean =
        oldItem == newItem
}

sealed class TravelsDataItem {
    data class CoverItem(val cover: TravelsCover) : TravelsDataItem() {
        override val id = cover.coverId
    }

    data class DescriptionItem(val description: TravelsDescription) : TravelsDataItem() {
        override val id = description.descriptionId
    }

    data class AdItem(val ad: Ad) : TravelsDataItem() {
        override val id = ad.addId
    }

    abstract val id: String
}

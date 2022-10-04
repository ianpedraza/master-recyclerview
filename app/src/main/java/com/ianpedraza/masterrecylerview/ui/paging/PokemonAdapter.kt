package com.ianpedraza.masterrecylerview.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.databinding.ItemPokemonBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.capitalizeFirst
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadImageByUrl
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.setCardBackgroundDominantColorFrom
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.toPokedexNumber

class PokemonAdapter : PagingDataAdapter<Pokemon, PokemonAdapter.ViewHolder>(PokemonDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { pokemon -> holder.bind(pokemon) }
    }

    class ViewHolder(
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPokemonBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(pokemon: Pokemon): Unit = with(pokemon) {
            binding.tvNamePokemonItem.text = name.capitalizeFirst()
            binding.tvNumberPokemonItem.text = id.toPokedexNumber()

            image?.let {
                binding.ivPokemonItem.loadImageByUrl(image) { drawable ->
                    drawable?.let {
                        binding.cvPokemonItem.setCardBackgroundDominantColorFrom(drawable.toBitmap())
                    }
                }
            }
        }
    }
}

private object PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(
        oldItem: Pokemon,
        newItem: Pokemon
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Pokemon,
        newItem: Pokemon
    ) = oldItem == newItem
}

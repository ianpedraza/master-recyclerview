package com.ianpedraza.masterrecylerview.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.databinding.ItemPokemonSearchBinding
import com.ianpedraza.masterrecylerview.ui.paging.PokemonAction
import com.ianpedraza.masterrecylerview.ui.paging.PokemonDiffCallback
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.capitalizeFirst
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.getDominantColor
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadImageByUrl
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.toPokedexNumber

class PokemonSearchAdapter(
    private val onAction: (PokemonAction) -> Unit
) : PagingDataAdapter<Pokemon, PokemonSearchAdapter.ViewHolder>(PokemonDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { pokemon -> holder.bind(pokemon, onAction) }
    }

    class ViewHolder(
        private val binding: ItemPokemonSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPokemonSearchBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Pokemon, onAction: (PokemonAction) -> Unit): Unit = with(item) {
            binding.root.setOnClickListener { onAction(PokemonAction.OnClick(item)) }
            binding.textViewPokemonSearchName.text = name.capitalizeFirst()
            binding.textViewPokemonSearchNumber.text = id.toPokedexNumber()

            image?.let {
                binding.imageViewPokemonSearch.loadImageByUrl(image, false) { drawable ->
                    drawable?.toBitmap()?.getDominantColor { color ->
                        binding.root.setBackgroundColor(color)
                    }
                }
            }
        }
    }
}

package com.ianpedraza.masterrecylerview.ui.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.domain.pokemon.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel
@Inject
constructor(private val repository: PokemonRepository) : ViewModel() {
    val pokemonList: LiveData<PagingData<Pokemon>> =
        repository.getAllPokemon().cachedIn(viewModelScope)
}

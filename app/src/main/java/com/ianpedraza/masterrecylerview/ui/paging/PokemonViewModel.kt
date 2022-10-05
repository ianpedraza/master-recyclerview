package com.ianpedraza.masterrecylerview.ui.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.usecases.GetAllPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel
@Inject
constructor(getAllPokemonUseCase: GetAllPokemonUseCase) : ViewModel() {
    private var _pokemonList: LiveData<PagingData<Pokemon>> =
        getAllPokemonUseCase().liveData.cachedIn(viewModelScope)

    val pokemonList: LiveData<PagingData<Pokemon>> = _pokemonList
}

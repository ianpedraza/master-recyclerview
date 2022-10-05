package com.ianpedraza.masterrecylerview.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.usecases.SearchPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val EMPTY_STRING = ""

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonSearchViewModel
@Inject
constructor(
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val _query = MutableStateFlow<String?>(EMPTY_STRING)

    private val _pokemonList = _query.flatMapLatest { query ->
        searchPokemonUseCase(query).flow
    }.cachedIn(viewModelScope)

    val pokemonList: Flow<PagingData<Pokemon>> = _pokemonList

    fun searchPokemon(query: String?) {
        _query.tryEmit(query)
    }
}

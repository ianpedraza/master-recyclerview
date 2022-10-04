package com.ianpedraza.masterrecylerview.data.pokemon.models.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ianpedraza.masterrecylerview.data.pokemon.api.PokemonApi

class PokemonDataSourceFactory(
    private val service: PokemonApi
) {

    private val _datasource = MutableLiveData<PokemonPagingDataSource>()
    val datasource: LiveData<PokemonPagingDataSource> = _datasource

    fun create(): PokemonPagingDataSource {
        val pokemonPagingDataSource = PokemonPagingDataSource(service)
        _datasource.value = pokemonPagingDataSource
        return pokemonPagingDataSource
    }
}

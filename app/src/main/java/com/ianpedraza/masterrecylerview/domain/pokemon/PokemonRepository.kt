package com.ianpedraza.masterrecylerview.domain.pokemon

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ianpedraza.masterrecylerview.data.pokemon.api.PokemonApi
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.data.pokemon.models.remote.PokemonPagingDataSource
import javax.inject.Inject

class PokemonRepository
@Inject
constructor(
    private val service: PokemonApi
) {
    fun getAllPokemon(): LiveData<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PokemonPagingDataSource(service) }
        ).liveData
    }

    /*fun getAllPokemon(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                PokemonPagingDataSource(service)
            }
        ).flow
    }*/
}

package com.ianpedraza.masterrecylerview.domain.pokemon

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ianpedraza.masterrecylerview.data.pokemon.api.PokemonApi
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.data.pokemon.models.remote.PokemonPagingDataSource
import javax.inject.Inject

class PokemonRepository
@Inject
constructor(private val service: PokemonApi) {
    private val config = PagingConfig(pageSize = 20, prefetchDistance = 2)

    fun getAllPokemon(): Pager<Int, Pokemon> {
        return Pager(
            config = config,
            pagingSourceFactory = { PokemonPagingDataSource(service) }
        )
    }

    fun searchPokemon(query: String?): Pager<Int, Pokemon> {
        return Pager(
            config = config,
            pagingSourceFactory = { PokemonPagingDataSource(service, query) }
        )
    }
}

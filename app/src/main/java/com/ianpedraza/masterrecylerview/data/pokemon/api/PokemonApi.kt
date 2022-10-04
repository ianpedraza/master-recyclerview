package com.ianpedraza.masterrecylerview.data.pokemon.api

import com.ianpedraza.masterrecylerview.data.pokemon.models.remote.ListPokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE_KEY = "offset"

interface PokemonApi {
    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query(PAGE_KEY)
        offset: Int = 0,

        @Query("limit")
        limit: Int = 20
    ): ListPokemonResponse
}

package com.ianpedraza.masterrecylerview.data.pokemon.models.remote

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ianpedraza.masterrecylerview.data.pokemon.api.PAGE_KEY
import com.ianpedraza.masterrecylerview.data.pokemon.api.PokemonApi
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.domain.mappers.pokemon.PokemonMappers.Companion.search
import com.ianpedraza.masterrecylerview.domain.mappers.pokemon.PokemonMappers.Companion.toModel

class PokemonPagingDataSource(
    private val service: PokemonApi,
    private val query: String? = null
) : PagingSource<Int, Pokemon>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val pageNumber = params.key ?: 0
            val response = service.getAllPokemon(offset = pageNumber)
            val data = response.results.toModel().search(query)

            var previousPageNumber: Int? = null

            if (response.previous != null) {
                val uri = Uri.parse(response.previous)
                val previousPageQuery = uri.getQueryParameter(PAGE_KEY)
                previousPageNumber = previousPageQuery?.toInt()
            }

            var nextPageNumber: Int? = null

            if (response.next != null) {
                val uri = Uri.parse(response.next)
                val nextPageQuery = uri.getQueryParameter(PAGE_KEY)
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data,
                prevKey = previousPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? = state.anchorPosition
}

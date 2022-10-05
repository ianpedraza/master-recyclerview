package com.ianpedraza.masterrecylerview.usecases

import androidx.paging.Pager
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.domain.pokemon.PokemonRepository
import javax.inject.Inject

class SearchPokemonUseCase
@Inject
constructor(private val repository: PokemonRepository) {
    operator fun invoke(query: String?): Pager<Int, Pokemon> = repository.searchPokemon(query)
}

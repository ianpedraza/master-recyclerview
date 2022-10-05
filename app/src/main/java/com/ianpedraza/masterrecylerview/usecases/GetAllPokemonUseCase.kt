package com.ianpedraza.masterrecylerview.usecases

import androidx.paging.Pager
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.domain.pokemon.PokemonRepository
import javax.inject.Inject

class GetAllPokemonUseCase
@Inject
constructor(private val repository: PokemonRepository) {
    operator fun invoke(): Pager<Int, Pokemon> = repository.getAllPokemon()
}

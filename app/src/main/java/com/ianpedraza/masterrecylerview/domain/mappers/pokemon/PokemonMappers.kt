package com.ianpedraza.masterrecylerview.domain.mappers.pokemon

import com.ianpedraza.masterrecylerview.data.pokemon.models.local.BASE_IMAGE_URL
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.BASE_IMAGE_URL_REPLACEMENT
import com.ianpedraza.masterrecylerview.data.pokemon.models.local.Pokemon
import com.ianpedraza.masterrecylerview.data.pokemon.models.remote.PokemonResponse

class PokemonMappers {
    companion object {
        fun PokemonResponse.toModel(): Pokemon {
            val url = url.dropLast(1)
            val id = url.substring(url.lastIndexOf("/") + 1)

            return Pokemon(
                id = id.toInt(),
                name = name,
                image = BASE_IMAGE_URL.replace(BASE_IMAGE_URL_REPLACEMENT, id)
            )
        }

        fun List<PokemonResponse>.toModel(): List<Pokemon> {
            val modelList = map { responseItem ->
                responseItem.toModel()
            }

            return modelList
        }
    }
}

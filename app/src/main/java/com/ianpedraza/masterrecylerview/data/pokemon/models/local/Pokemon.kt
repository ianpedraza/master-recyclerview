package com.ianpedraza.masterrecylerview.data.pokemon.models.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val BASE_IMAGE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/*.png"

// const val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/*.png"
const val BASE_IMAGE_URL_REPLACEMENT = "*"

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val image: String?
) : Parcelable

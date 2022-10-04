package com.ianpedraza.masterrecylerview.data.pokemon.models.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("url")
    @Expose
    val url: String
)

package com.ianpedraza.masterrecylerview.data.travels

import java.util.*

data class TravelsCover(
    val coverId: String = UUID.randomUUID().toString(),
    val title: String,
    val headline: String,
    val image: String
)

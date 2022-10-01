package com.ianpedraza.masterrecylerview.data.travels

import java.util.*

data class TravelsDescription(
    val descriptionId: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String
)

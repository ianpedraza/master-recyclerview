package com.ianpedraza.masterrecylerview.data.travels

import java.util.UUID

data class Ad(
    val addId: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String
)

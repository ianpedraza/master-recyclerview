package com.ianpedraza.masterrecylerview.data.photos

object PhotosPicsumDummyData {
    private val heightRange = (200..380)

    val photos = (1..50).map { id ->
        Photo(
            id = id,
            image = "https://picsum.photos/seed/$id/200/${heightRange.random()}"
        )
    }.toList()
}

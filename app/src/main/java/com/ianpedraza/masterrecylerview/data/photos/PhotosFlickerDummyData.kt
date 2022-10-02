package com.ianpedraza.masterrecylerview.data.photos

object PhotosFlickerDummyData {
    val photos = (0..100).map { id ->
        Photo(
            id = id,
            image = "https://loremflickr.com/320/240/painting?lock=$id"
        )
    }.toList()
}

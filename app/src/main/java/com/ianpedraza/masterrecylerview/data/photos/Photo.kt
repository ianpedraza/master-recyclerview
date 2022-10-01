package com.ianpedraza.masterrecylerview.data.photos

data class Photo(
    val id: Int,
    val image: String = "https://loremflickr.com/320/240/painting?lock=$id"
)

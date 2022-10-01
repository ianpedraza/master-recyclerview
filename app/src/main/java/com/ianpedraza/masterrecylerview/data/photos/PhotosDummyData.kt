package com.ianpedraza.masterrecylerview.data.photos

object PhotosDummyData {
    val photos = (0..100).map { number -> Photo(id = number) }.toList()
}

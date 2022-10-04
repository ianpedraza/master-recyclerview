package com.ianpedraza.masterrecylerview.data.chats

import com.ianpedraza.masterrecylerview.utils.RandomMessage
import com.ianpedraza.masterrecylerview.utils.RandomNames

object ChatsDummyData {
    private const val REPLACEMENT = "*"
    private const val BASE_URL = "https://loremflickr.com/320/240/people?lock=$REPLACEMENT"

    val chats = (1L..30L).map { id ->
        ChatPreview(
            id = id,
            image = BASE_URL.replace(REPLACEMENT, id.toString()),
            name = RandomNames.generate(),
            message = RandomMessage.generate()
        )
    }.toList()
}

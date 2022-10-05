package com.ianpedraza.masterrecylerview.data.contacts

import com.ianpedraza.masterrecylerview.utils.ContactsUtils
import com.ianpedraza.masterrecylerview.utils.RandomNames

object ContactsDummyData {
    private const val REPLACEMENT = "*"
    private const val BASE_URL = "https://loremflickr.com/320/240/people?lock=$REPLACEMENT"

    val contacts = (1..30).map {
        val name = RandomNames.generate()

        Contact(
            id = System.currentTimeMillis(),
            name = name,
            photo = BASE_URL.replace(REPLACEMENT, it.toString()),
            phone = ContactsUtils.generateRandomPhone(),
            email = ContactsUtils.generateMailFrom(name)
        )
    }.toList()
}

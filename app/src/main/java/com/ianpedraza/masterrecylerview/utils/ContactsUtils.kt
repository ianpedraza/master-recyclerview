package com.ianpedraza.masterrecylerview.utils

class ContactsUtils {
    companion object {
        fun generateMailFrom(value: String): String {
            val nonAlphaNum = "[^a-zA-Z0-9]".toRegex()

            value.apply {
                replace(nonAlphaNum, "")
            }

            return "$value@gmail.com"
        }

        fun generateRandomPhone(): String {
            val range = (0..9)

            val number = (1..9).fold("") { accumulator, _ ->
                accumulator + range.random()
            }

            return number
        }
    }
}

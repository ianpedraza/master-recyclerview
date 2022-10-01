package com.ianpedraza.masterrecylerview.data.tasks

import java.util.*

private const val EMPTY_STRING = ""

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String = EMPTY_STRING,
    val body: String = EMPTY_STRING,
    val done: Boolean = false
)

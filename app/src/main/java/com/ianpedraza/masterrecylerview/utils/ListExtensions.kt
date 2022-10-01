package com.ianpedraza.masterrecylerview.utils

class ListExtensions {
    companion object {
        fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
            val tmp = this[index1]
            this[index1] = this[index2]
            this[index2] = tmp
        }
    }
}

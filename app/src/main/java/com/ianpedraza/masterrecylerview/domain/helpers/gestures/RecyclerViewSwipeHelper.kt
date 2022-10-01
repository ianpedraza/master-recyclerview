package com.ianpedraza.masterrecylerview.domain.helpers.gestures

import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewSwipeHelper {
    fun onSwipeStart(viewHolder: RecyclerView.ViewHolder)
    fun onSwipeEnd(viewHolder: RecyclerView.ViewHolder)
}

package com.ianpedraza.masterrecylerview.domain.helpers.gestures

import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewDragHelper {
    fun onDrag(viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)
}

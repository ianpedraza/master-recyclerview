package com.ianpedraza.masterrecylerview.domain.helpers.gestures

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewDragDataHelper() :
    SimpleCallback(UP or DOWN, 0),
    RecyclerViewDragHelper {

    final override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onDrag(viewHolder, target)
        return true
    }

    final override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    final override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) = super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    override fun onDrag(viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = Unit
}

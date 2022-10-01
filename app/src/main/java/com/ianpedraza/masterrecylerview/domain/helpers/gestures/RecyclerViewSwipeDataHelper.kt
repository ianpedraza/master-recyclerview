package com.ianpedraza.masterrecylerview.domain.helpers.gestures

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class RecyclerViewSwipeDataHelper(
    private val startHolder: SwipeHolder,
    private val endHolder: SwipeHolder
) : SimpleCallback(0, START or END), RecyclerViewSwipeHelper {

    final override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean = false

    final override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        when (direction) {
            START -> onSwipeStart(viewHolder)
            END -> onSwipeEnd(viewHolder)
        }
    }

    final override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val backgroundOffset = 20

        val icon: Drawable = when {
            dX > 0 -> startHolder.buildIcon()
            else -> endHolder.buildIcon()
        }

        val background: ColorDrawable = when {
            dX > 0 -> startHolder.backgroundColor
            else -> endHolder.backgroundColor
        }

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        if (dX > 0) {
            val iconLeft = itemView.left + iconMargin
            val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt() + backgroundOffset,
                itemView.bottom
            )

            /*
            paint.color = holder.backgroundColor
            c.drawRect(
                itemView.left.toFloat(),
                itemView.top.toFloat(),
                dX,
                itemView.bottom.toFloat(),
                paint
            )

            c.drawBitmap(
                holder.icon,
                itemView.left.toFloat() + margin,
                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - holder.icon.height) / 2,
                paint
            )
            */
        } else if (dX < 0) {
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(
                itemView.right + dX.toInt() - backgroundOffset,
                itemView.top,
                itemView.right,
                itemView.bottom
            )

            /*
            paint.color = swipeHolder.backgroundColor

            c.drawRect(
                itemView.right.toFloat() + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat(),
                paint
            )

            c.drawBitmap(
                swipeHolder.icon,
                itemView.right.toFloat() - margin - swipeHolder.icon.width,
                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - swipeHolder.icon.height) / 2,
                paint
            )
            */
        } else {
            icon.setBounds(0, 0, 0, 0)
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)
    }

    override fun onSwipeStart(viewHolder: ViewHolder) = Unit

    override fun onSwipeEnd(viewHolder: ViewHolder) = Unit

    data class SwipeHolder(
        val backgroundColor: ColorDrawable,
        val icon: Drawable,
        @ColorInt
        val iconColor: Int = Color.BLACK
    ) {
        fun buildIcon() = icon.apply { setTint(iconColor) }
    }
}

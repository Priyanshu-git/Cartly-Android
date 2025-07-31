package com.nexxlabs.cartly.main.groupdetail.ui


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemSwipeCallback private constructor(
    context: Context,
    private val swipeThreshold:Float?,
    private val leftConfig: SwipeConfig?,
    private val rightConfig: SwipeConfig?,
    private val onItemSwiped: (position: Int, direction: Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, getSwipeDirs(leftConfig, rightConfig)) {

    companion object {
        private fun getSwipeDirs(left: SwipeConfig?, right: SwipeConfig?): Int {
            var dir = 0
            if (left != null) dir = dir or ItemTouchHelper.LEFT
            if (right != null) dir = dir or ItemTouchHelper.RIGHT
            return dir
        }
    }

    private data class SwipeConfig(
        val bgColor: Int,
        val iconRes: Int
    )

    private val leftPaint = Paint().apply {
        color = leftConfig?.bgColor ?: Color.TRANSPARENT
    }

    private val rightPaint = Paint().apply {
        color = rightConfig?.bgColor ?: Color.TRANSPARENT
    }

    private val leftIcon: Drawable? = leftConfig?.iconRes?.let {
        ContextCompat.getDrawable(context, it)
    }

    private val rightIcon: Drawable? = rightConfig?.iconRes?.let {
        ContextCompat.getDrawable(context, it)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemSwiped(viewHolder.adapterPosition, direction)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val iconMargin = 32

        val iconTop = itemView.top + (itemView.height - (rightIcon?.intrinsicHeight ?: 0)) / 2
        val iconBottom = iconTop + (rightIcon?.intrinsicHeight ?: 0)

        if (dX > 0 && rightConfig != null) {
            // Swiping Right
            c.drawRect(
                itemView.left.toFloat(),
                itemView.top.toFloat(),
                itemView.left + dX,
                itemView.bottom.toFloat(),
                rightPaint
            )

            rightIcon?.let {
                val iconLeft = itemView.left + iconMargin
                val iconRight = iconLeft + it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                it.draw(c)
            }
        } else if (dX < 0 && leftConfig != null) {
            // Swiping Left
            c.drawRect(
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat(),
                leftPaint
            )

            leftIcon?.let {
                val iconRight = itemView.right - iconMargin
                val iconLeft = iconRight - it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                it.draw(c)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold ?: super.getSwipeThreshold(viewHolder)
    }

    class Builder(private val context: Context) {
        private var leftConfig: SwipeConfig? = null
        private var rightConfig: SwipeConfig? = null
        private lateinit var onSwiped: (position: Int, direction: Int) -> Unit
        private var swipeThreshold: Float? = null

        fun setLeftSwipe(bgColorRes: Int, iconRes: Int) = apply {
            leftConfig = SwipeConfig(context.getColor(bgColorRes), iconRes)
        }

        fun setRightSwipe(bgColorRes: Int, iconRes: Int) = apply {
            rightConfig = SwipeConfig(context.getColor(bgColorRes), iconRes)
        }

        fun setOnSwipeListener(listener: (position: Int, direction: Int) -> Unit) = apply {
            onSwiped = listener
        }

        fun setSwipeThreshold(threshold: Float) = apply {
            swipeThreshold = threshold
        }

        fun build(): ItemSwipeCallback {
            require(::onSwiped.isInitialized) { "onSwiped listener must be set." }
            return ItemSwipeCallback(context,swipeThreshold, leftConfig, rightConfig, onSwiped)
        }
    }
}


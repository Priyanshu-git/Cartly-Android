package com.nexxlabs.cartly.main.dashboard.ui

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.nexxlabs.cartly.R

class MemberChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : Chip(context, attrs) {

    init {
        isCloseIconVisible = true
        ellipsize = TextUtils.TruncateAt.END
        isClickable = true
        isCheckable = false

        setTextColor(ContextCompat.getColor(context, R.color.chip_text))
        chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.chip_background)
        )
        closeIconTint = ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.chip_close_icon_tint)
        )
        contentDescription = context.getString(R.string.remove_chip_content_desc)
    }

    fun setEmail(email: String) {
        text = email
    }

    fun setChipColors(
        @ColorRes bg: Int,
        @ColorRes textColor: Int,
        @ColorRes closeTint: Int = R.color.chip_close_icon_tint
    ) {
        chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, bg))
        setTextColor(ContextCompat.getColor(context, textColor))
        closeIconTint = ColorStateList.valueOf(ContextCompat.getColor(context, closeTint))
    }
}


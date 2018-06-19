package com.nakharin.mylibrary.compoment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.nakharin.mylibrary.R
import kotlinx.android.synthetic.main.view_custom_compoment_toolbar.view.*

class CustomToolbar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_compoment_toolbar, this, true)

        attrs?.let {
            val a = context.theme.obtainStyledAttributes(it, R.styleable.CustomToolbar, defStyleAttr, 0)

            if (a.hasValue(R.styleable.CustomToolbar_item_count)) {
                val itemCount: Int = a.getInt(R.styleable.CustomToolbar_item_count, 0)
                checkLess100(itemCount)
            }

            if (a.hasValue(R.styleable.CustomToolbar_show_back_icon)) {
                val isShow = a.getBoolean(R.styleable.CustomToolbar_show_back_icon, false)
                checkShowBackIcon(isShow)
            }

            a.recycle()
        }
    }

    private fun checkLess100(itemCount: Int) {
        when (itemCount) {
            in 1..99 -> {
                fmlBadgeCount.visibility = View.VISIBLE
                txtBadgeCount.text = itemCount.toString()
            }
            else -> {
                fmlBadgeCount.visibility = View.GONE
            }
        }
    }

    private fun checkShowBackIcon(isShow: Boolean) {
        if (isShow) {
            imgBackIcon.visibility = View.VISIBLE
        } else {
            imgBackIcon.visibility = View.GONE
        }
    }

    fun setItemCount(itemCount: Int) {
        checkLess100(itemCount)
    }

    fun isShowBackIcon(isShow: Boolean) {
        checkShowBackIcon(isShow)
    }
}
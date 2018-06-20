package com.nakharin.mylibrary.compoment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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

    private var onToolbarClickListener: OnToolbarClickListener? = null

    private var itemCount: Int = 0

    private var isShow: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_compoment_toolbar, this, true)

        attrs?.let {
            val a = context.theme.obtainStyledAttributes(it, R.styleable.CustomToolbar, defStyleAttr, 0)

            if (a.hasValue(R.styleable.CustomToolbar_item_count)) {
                itemCount = a.getInt(R.styleable.CustomToolbar_item_count, 0)
                checkLess100(itemCount)
            }

            if (a.hasValue(R.styleable.CustomToolbar_show_back_icon)) {
                isShow = a.getBoolean(R.styleable.CustomToolbar_show_back_icon, false)
                checkShowBackIcon(isShow)
            }

            a.recycle()
        }

        val onClickListener: View.OnClickListener = OnClickListener {
            when(it) {
                imgBackIcon -> onToolbarClickListener?.onBackClickListener()
                frmBadgeCount -> onToolbarClickListener?.onBadgeClickListener()
            }
        }

        imgBackIcon.setOnClickListener(onClickListener)
        frmBadgeCount.setOnClickListener(onClickListener)
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

    fun setOnToolbarClickListener(onToolbarClickListener: OnToolbarClickListener) {
        this.onToolbarClickListener = onToolbarClickListener
    }

    interface OnToolbarClickListener {
        fun onBackClickListener()
        fun onBadgeClickListener()
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putBoolean("show", isShow)
        bundle.putInt("itemCount", itemCount)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewState = state
        if (viewState is Bundle) {
            isShow = viewState.getBoolean("show", false)
            itemCount = viewState.getInt("itemCount", 0)
            viewState = viewState.getParcelable("superState")
        }
        super.onRestoreInstanceState(viewState)
    }
}
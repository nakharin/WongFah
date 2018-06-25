package com.nakharin.wongfah.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.nakharin.wongfah.network.model.JsonCategory
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_recycler_category_item_row.*

class CategoryViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(category: JsonCategory) {
        Glide.with(containerView.context).load(category.imageUrl).into(imgCategory)
        txtCategory.text = category.name
    }
}
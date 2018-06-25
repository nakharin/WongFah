package com.nakharin.wongfah.adapter

import android.content.Context
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nakharin.wongfah.R
import com.nakharin.wongfah.adapter.diffutil.CategoryDiffCallback
import com.nakharin.wongfah.adapter.viewholder.CategoryViewHolder
import com.nakharin.wongfah.network.model.JsonCategory

class CategoryListAdapter : ListAdapter<JsonCategory, RecyclerView.ViewHolder>(AsyncDifferConfig.Builder(CategoryDiffCallback()).build()) {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext)
        return CategoryViewHolder(layoutInflater.inflate(R.layout.view_recycler_category_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryViewHolder) {
            val jsonCategory = getItem(position) as JsonCategory
            holder.bind(jsonCategory)
        }
    }
}
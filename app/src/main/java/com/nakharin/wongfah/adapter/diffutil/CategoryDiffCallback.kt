package com.nakharin.wongfah.adapter.diffutil

import android.support.v7.util.DiffUtil
import com.nakharin.wongfah.network.model.JsonCategory

class CategoryDiffCallback : DiffUtil.ItemCallback<JsonCategory>() {

    override fun areItemsTheSame(oldItem: JsonCategory, newItem: JsonCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JsonCategory, newItem: JsonCategory): Boolean {
        return oldItem.name == newItem.name && oldItem.imageUrl == newItem.imageUrl && oldItem.menus == newItem.menus && oldItem.menus.size == newItem.menus.size
    }
}
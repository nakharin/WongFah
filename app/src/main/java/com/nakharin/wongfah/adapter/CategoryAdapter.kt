package com.nakharin.wongfah.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nakharin.wongfah.R
import com.nakharin.wongfah.network.model.JsonCategory
import java.util.*

class CategoryAdapter(private val categoryList: ArrayList<JsonCategory>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_recycler_category_item_row, parent, false))
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(mContext)
                .load(categoryList[position].imageUrl)
                .into(holder.imgCategory)

        holder.txtCategory.text = categoryList[position].name
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgCategory = v.findViewById<ImageView>(R.id.imgCategory)!!
        val txtCategory = v.findViewById<TextView>(R.id.txtCategory)!!
    }
}
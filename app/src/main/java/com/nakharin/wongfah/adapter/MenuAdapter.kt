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
import com.nakharin.wongfah.network.model.JsonMenu
import java.util.*

class MenuAdapter(private val menuList: ArrayList<JsonMenu>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_recycler_menu_screen_item_row, parent, false))
    }


    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(mContext)
                .load(menuList[position].imageUrl)
                .into(holder.imgMenu)

        holder.txtMenu.text = menuList[position].name
        holder.txtPrice.text = menuList[position].price.toString()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgMenu = v.findViewById<ImageView>(R.id.imgMenu)!!
        val txtMenu = v.findViewById<TextView>(R.id.txtMenu)!!
        val txtPrice = v.findViewById<TextView>(R.id.txtPrice)!!
        val txtBath = v.findViewById<TextView>(R.id.txtBath)!!
    }
}
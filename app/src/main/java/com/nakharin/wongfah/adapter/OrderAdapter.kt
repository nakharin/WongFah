package com.nakharin.wongfah.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nakharin.wongfah.R
import com.nakharin.wongfah.network.model.JsonMenu
import kotlinx.android.synthetic.main.view_recycler_order_item_row.view.*
import java.util.*

class OrderAdapter(private val orderList: ArrayList<JsonMenu>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    private var mOnRemoveListener: OnRemoveListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_recycler_order_item_row, parent, false))
    }


    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(mContext)
                .load(orderList[position].imageUrl)
                .into(holder.imgOrder)

        holder.txtMenu.text = orderList[position].name
        holder.txtPrice.text = orderList[position].price.toString()

        holder.linOrder.setOnClickListener {
            holder.crvDelete.visibility = View.GONE
        }

        holder.imgMore.setOnClickListener {
            if (holder.crvDelete.visibility == View.VISIBLE) {
                holder.crvDelete.visibility = View.GONE
            } else {
                holder.crvDelete.visibility = View.VISIBLE
            }
        }

        holder.crvDelete.setOnClickListener {
            holder.crvDelete.visibility = View.GONE
            mOnRemoveListener?.onRemoved(holder.adapterPosition)
        }
    }

    fun setOnRemoveListener(onRemoveListener: OnRemoveListener) {
     mOnRemoveListener = onRemoveListener
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val linOrder = v.linOrder!!
        val imgOrder = v.imgOrder!!
        val txtMenu = v.txtMenu!!
        val txtPrice = v.txtPrice!!
        val txtBath = v.txtBath!!
        val imgMore = v.imgMore!!
        val crvDelete = v.crvDelete!!
    }

    interface OnRemoveListener {
        fun onRemoved(position: Int)
    }
}
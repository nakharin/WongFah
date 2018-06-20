package com.nakharin.wongfah.controller.fragment

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nakharin.mylibrary.view.DialogLoadingFragment
import com.nakharin.wongfah.R
import com.nakharin.wongfah.adapter.OrderAdapter
import com.nakharin.wongfah.network.model.JsonMenu
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_order_list.*
import org.parceler.Parcels

class OrderListFragment: Fragment() {

    companion object {
        fun newInstance(wrapped: Parcelable): OrderListFragment {
            val fragment = OrderListFragment()
            val args = Bundle()
            args.putParcelable("SelectedMenuList", wrapped)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerOrder: RecyclerView

    private lateinit var orderAdapter: OrderAdapter

    private var menuList: ArrayList<JsonMenu> = arrayListOf()

    private lateinit var dialog: DialogLoadingFragment

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)

        arguments?.let {
            val menus = Parcels.unwrap<ArrayList<JsonMenu>>(it.getParcelable("SelectedMenuList"))
            menuList.addAll(menus)
        }

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_order_list, container, false)
        initInstances(rootView, savedInstanceState)
        setUpView()
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        recyclerOrder = rootView.findViewById(R.id.recyclerOrder)
        recyclerOrder.isNestedScrollingEnabled = false
        val linearLayoutManager = LinearLayoutManager(rootView.context)
        recyclerOrder.layoutManager = linearLayoutManager

        orderAdapter = OrderAdapter(menuList)
        recyclerOrder.adapter = orderAdapter
    }

    private fun setUpView() {
        dialog = DialogLoadingFragment.newInstance(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerOrder.adapter.notifyDataSetChanged()

        if (menuList.isNotEmpty()) {
            calculate()
        }
    }

    private fun calculate() {
        var netCost = 0.0
        var vatCost = 0.0
        var serviceChargeCost = 0.0
        var totalCost = 0.0
        menuList.forEach {
            netCost += it.price
        }
        txtNet.text = "%.2f".format(netCost)

        vatCost = (netCost * 7) / 100
        txtVat.text = "%.2f".format(vatCost)

        serviceChargeCost = (netCost * 10) / 100
        txtServiceCharge.text = "%.2f".format(serviceChargeCost)

        totalCost = netCost + vatCost + serviceChargeCost
        txtTotal.text = "%.2f".format(totalCost)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }
}
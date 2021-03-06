package com.nakharin.wongfah.controller.fragment

import android.os.Bundle
import android.os.Handler
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
import com.nakharin.wongfah.event.EventSendCheckOut
import com.nakharin.wongfah.event.EventSendDeletePosition
import com.nakharin.wongfah.manager.bus.BusProvider
import com.nakharin.wongfah.network.model.JsonMenu
import com.nakharin.wongfah.presenter.CostCalculator
import com.nakharin.wongfah.utility.Constants
import com.pawegio.kandroid.longToast
import kotlinx.android.synthetic.main.fragment_order_list.*
import org.parceler.ParcelerRuntimeException
import org.parceler.Parcels

class OrderListFragment : Fragment() {

    companion object {
        fun newInstance(): OrderListFragment {
            val fragment = OrderListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(wrapped: Parcelable): OrderListFragment {
            val fragment = OrderListFragment()
            val args = Bundle()
            args.putParcelable(Constants.SELECTED_MENU_LIST, wrapped)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerOrder: RecyclerView

    private lateinit var orderAdapter: OrderAdapter

    private var menuList: ArrayList<JsonMenu> = arrayListOf()

    private lateinit var dialog: DialogLoadingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)

        arguments?.let {
            try {
                val menus = Parcels.unwrap<ArrayList<JsonMenu>>(it.getParcelable(Constants.SELECTED_MENU_LIST))
                if (menus != null) {
                    menuList.addAll(menus)
                }
            } catch (e: ParcelerRuntimeException) {
            }
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

        orderAdapter.setOnRemoveListener(onRemoveListener)
    }

    private fun setUpView() {
        dialog = DialogLoadingFragment.newInstance(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Method from this class
        calculate()

        recyclerOrder.adapter.notifyDataSetChanged()

        btnCheckOut.setOnClickListener(onClickListener)
    }

    private fun calculate() {
        val cost = CostCalculator(menuList)
        txtNet.text = "%.2f".format(cost.onNetChanged())
        txtVat.text = "%.2f".format(cost.onVatChanged())
        txtServiceCharge.text = "%.2f".format(cost.onServiceChargeChanged())
        txtTotal.text = "%.2f".format(cost.onTotalChanged())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
        outState.putParcelable(Constants.SELECTED_MENU_LIST, Parcels.wrap(menuList))
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }

    /********************************************************************************************
     ************************************ Listener **********************************************
     ********************************************************************************************/

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        if (menuList.isNotEmpty()) {
            dialog.show(fragmentManager, "dialog")
            Handler().postDelayed({
                dialog.dismiss()
                val eventSendCheckOut = EventSendCheckOut()
                eventSendCheckOut.isCheckOut = true
                BusProvider.getInstance().post(eventSendCheckOut)
            }, 1500)
        } else {
            longToast(getString(R.string.str_please_select_foods))
        }
    }

    private val onRemoveListener: OrderAdapter.OnRemoveListener = object : OrderAdapter.OnRemoveListener {
        override fun onRemoved(position: Int) {
            menuList.removeAt(position)
            recyclerOrder.adapter.notifyItemRemoved(position)

            val eventSendDeletePosition = EventSendDeletePosition()
            eventSendDeletePosition.position = position
            BusProvider.getInstance().post(eventSendDeletePosition)

            // Method from this class
            calculate()

            if (menuList.isEmpty()) {
                btnCheckOut.visibility = View.GONE
            }
        }
    }
}
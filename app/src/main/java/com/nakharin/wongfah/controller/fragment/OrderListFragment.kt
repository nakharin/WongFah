package com.nakharin.wongfah.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nakharin.mylibrary.view.DialogLoadingFragment
import com.nakharin.wongfah.R
import com.nakharin.wongfah.adapter.MenuAdapter
import com.nakharin.wongfah.adapter.OrderAdapter
import com.nakharin.wongfah.network.ConnectionService
import com.nakharin.wongfah.network.model.JsonMenu
import com.pawegio.kandroid.longToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderListFragment: Fragment() {

    companion object {
        fun newInstance(): OrderListFragment {
            val fragment = OrderListFragment()
            val args = Bundle()
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

        dialog.show(fragmentManager, "dialog")

        val service = ConnectionService.getApiService().getCategoryList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    dialog.dismiss()
                    if (it.success) {
                        menuList.clear()
                        it.data?.let {

                            menuList.addAll(it[1].menus)
                        }
                        recyclerOrder.adapter.notifyDataSetChanged()
                    } else {
                        longToast(it.message)
                    }
                }, {
                    dialog.dismiss()
                    longToast(it.localizedMessage)
                })

        compositeDisposable.add(service)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
//        outState.putBoolean("isFirstLoad", isFirstLoad)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
//        isFirstLoad = savedInstanceState.getBoolean("isFirstLoad")
    }
}
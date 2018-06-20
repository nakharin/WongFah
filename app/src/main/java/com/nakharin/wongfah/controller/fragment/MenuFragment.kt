package com.nakharin.wongfah.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nakharin.mylibrary.view.DialogLoadingFragment
import com.nakharin.wongfah.R
import com.nakharin.wongfah.adapter.MenuAdapter
import com.nakharin.wongfah.addOnItemClickListener
import com.nakharin.wongfah.event.EventSendCompleted
import com.nakharin.wongfah.event.EventSendSelectMenu
import com.nakharin.wongfah.manager.CategoryManager
import com.nakharin.wongfah.manager.bus.BusProvider
import com.nakharin.wongfah.network.model.JsonMenu
import com.nakharin.wongfah.utility.Constants
import com.nakharin.wongfah.utility.RecyclerItemClickListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment: Fragment() {

    companion object {
        fun newInstance(position: Int): MenuFragment {
            val fragment = MenuFragment()
            val args = Bundle()
            args.putInt(Constants.POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerMenu: RecyclerView

    private lateinit var menuAdapter: MenuAdapter

    private var menuList: ArrayList<JsonMenu> = arrayListOf()

    private lateinit var dialog: DialogLoadingFragment

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)

        arguments?.let{
            position = it.getInt(Constants.POSITION, 0)
        }

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)
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

        recyclerMenu = rootView.findViewById(R.id.recyclerMenu)
        val linearLayoutManager = LinearLayoutManager(rootView.context, OrientationHelper.HORIZONTAL, false)
        recyclerMenu.layoutManager = linearLayoutManager

        menuAdapter = MenuAdapter(menuList)
        recyclerMenu.adapter = menuAdapter
    }

    private fun setUpView() {
        dialog = DialogLoadingFragment.newInstance(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadLocalMenuList()

        recyclerMenu.addOnItemClickListener(onItemClickListener)
        btnComplete.setOnClickListener(onClickListener)
    }

    private fun loadLocalMenuList() {
        val menus = CategoryManager.getInstance().getMenuList(position)
        menuList.addAll(menus)
        recyclerMenu.adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
        outState.putInt(Constants.POSITION, position)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
        position = savedInstanceState.getInt(Constants.POSITION)
    }

    /********************************************************************************************
     ************************************ Listener **********************************************
     ********************************************************************************************/

    private val onItemClickListener: RecyclerItemClickListener.OnClickListener = object : RecyclerItemClickListener.OnClickListener {
        override fun onItemClick(position: Int, view: View) {
            val eventSendSelectMenu = EventSendSelectMenu()
            eventSendSelectMenu.menu = menuList[position]
            BusProvider.getInstance().post(eventSendSelectMenu)
        }
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        when(it) {
            btnComplete -> {
                val eventSendCompleted = EventSendCompleted()
                eventSendCompleted.isCompleted = true
                BusProvider.getInstance().post(eventSendCompleted)
            }
        }
    }
}
package com.nakharin.wongfah.controller.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nakharin.mylibrary.compoment.CustomToolbar
import com.nakharin.wongfah.R
import com.nakharin.wongfah.controller.fragment.CategoryFragment
import com.nakharin.wongfah.controller.fragment.MenuFragment
import com.nakharin.wongfah.controller.fragment.OrderCompletedFragment
import com.nakharin.wongfah.controller.fragment.OrderListFragment
import com.nakharin.wongfah.event.*
import com.nakharin.wongfah.manager.bus.BusProvider
import com.nakharin.wongfah.network.model.JsonMenu
import com.pawegio.kandroid.longToast
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels

class MainActivity : AppCompatActivity() {

    private var menuList: ArrayList<JsonMenu> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        if (savedInstanceState == null) {
            myToolbar.isShowBackIcon(false)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer,
                            CategoryFragment.newInstance(),
                            "CategoryFragment")
                    .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        BusProvider.getInstance().register(this)
    }

    override fun onPause() {
        super.onPause()
        BusProvider.getInstance().unregister(this)
    }

    override fun onBackPressed() {
        return
    }

    private fun initToolbar() {
        myToolbar.setItemCount(0)
        myToolbar.setOnToolbarClickListener(onToolbarClickListener)
    }

    /********************************************************************************************
     ************************************ Listener **********************************************
     ********************************************************************************************/

    private val onToolbarClickListener: CustomToolbar.OnToolbarClickListener = object : CustomToolbar.OnToolbarClickListener {

        override fun onBackClickListener() {
            val fragment = supportFragmentManager.findFragmentById(R.id.contentContainer)
            if (fragment is MenuFragment || fragment is OrderListFragment || fragment is OrderCompletedFragment) {
                myToolbar.isShowBackIcon(false)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer,
                                CategoryFragment.newInstance(),
                                "CategoryFragment")
                        .commit()
            }
        }

        override fun onBadgeClickListener() {
            if (menuList.isNotEmpty()) {
                val fragment = supportFragmentManager.findFragmentById(R.id.contentContainer)
                if (fragment is CategoryFragment || fragment is MenuFragment) {
                    myToolbar.isShowBackIcon(true)
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.contentContainer,
                                    OrderListFragment.newInstance(Parcels.wrap(menuList)),
                                    "OrderListFragment")
                            .commit()
                }
            } else {
                longToast(getString(R.string.str_please_select_foods))
            }
        }
    }

    /********************************************************************************************
     ************************************ Event Bus *********************************************
     ********************************************************************************************/

    @Subscribe
    fun onRecivePosition(eventSendPosition: EventSendPosition) {
        myToolbar.isShowBackIcon(true)
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer,
                        MenuFragment.newInstance(eventSendPosition.position),
                        "MenuFragment")
                .commit()
    }

    @Subscribe
    fun onReciveSeletedMenu(eventSendSelectMenu: EventSendSelectMenu) {
        menuList.add(eventSendSelectMenu.menu)
        myToolbar.setItemCount(menuList.count())
    }

    @Subscribe
    fun onReciveCompleted(eventSendCompleted: EventSendCompleted) {
        if (eventSendCompleted.isCompleted) {
            if (menuList.isNotEmpty()) {
                val fragment = supportFragmentManager.findFragmentById(R.id.contentContainer)
                if (fragment is CategoryFragment || fragment is MenuFragment) {
                    myToolbar.isShowBackIcon(true)
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.contentContainer,
                                    OrderListFragment.newInstance(Parcels.wrap(menuList)),
                                    "OrderListFragment")
                            .commit()
                }
            } else {
                longToast(getString(R.string.str_please_select_foods))
            }
        }
    }

    @Subscribe
    fun onReciveCheckOut(eventSendCheckOut: EventSendCheckOut) {
        if (eventSendCheckOut.isCheckOut) {
            menuList.clear()
            myToolbar.setItemCount(0)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer,
                            OrderCompletedFragment.newInstance(),
                            "OrderCompletedFragment")
                    .commit()
        }
    }

    @Subscribe
    fun onReciveClosed(eventSendClosed: EventSendClosed) {
        if (eventSendClosed.isClosed) {
            myToolbar.isShowBackIcon(false)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer,
                            CategoryFragment.newInstance(),
                            "CategoryFragment")
                    .commit()
        }
    }
}

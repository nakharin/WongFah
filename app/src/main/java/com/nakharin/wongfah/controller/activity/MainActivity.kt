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

            val categoryFragment = CategoryFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer,
                            categoryFragment,
                            "CategoryFragment")
                    .commit()

            val menuFragment = MenuFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer,
                            menuFragment,
                            "MenuFragment")
                    .detach(menuFragment)
                    .commit()

            val orderListFragment = OrderListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer,
                            orderListFragment,
                            "OrderListFragment")
                    .detach(orderListFragment)
                    .commit()

            val orderCompletedFragment = OrderCompletedFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer,
                            orderCompletedFragment,
                            "OrderCompletedFragment")
                    .detach(orderCompletedFragment)
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

                val categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
                val menuFragment = supportFragmentManager.findFragmentByTag("MenuFragment")
                val orderListFragment = supportFragmentManager.findFragmentByTag("OrderListFragment")
                val orderCompletedFragment = supportFragmentManager.findFragmentByTag("OrderCompletedFragment")

                supportFragmentManager.beginTransaction()
                        .attach(categoryFragment)
                        .detach(menuFragment)
                        .detach(orderListFragment)
                        .detach(orderCompletedFragment)
                        .commit()
            }
        }

        override fun onBadgeClickListener() {
            if (menuList.isNotEmpty()) {
                val fragment = supportFragmentManager.findFragmentById(R.id.contentContainer)
                if (fragment is CategoryFragment || fragment is MenuFragment) {
                    myToolbar.isShowBackIcon(true)

                    val categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
                    val menuFragment = supportFragmentManager.findFragmentByTag("MenuFragment")
                    val orderListFragment = OrderListFragment.newInstance(Parcels.wrap(menuList))
                    val orderCompletedFragment = supportFragmentManager.findFragmentByTag("OrderCompletedFragment")

                    supportFragmentManager.beginTransaction()
                            .add(R.id.contentContainer,
                                    orderListFragment,
                                    "OrderListFragment")
                            .detach(categoryFragment)
                            .detach(menuFragment)
                            .detach(orderCompletedFragment)
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

        val categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
        val menuFragment = MenuFragment.newInstance(eventSendPosition.position)
        val orderListFragment = supportFragmentManager.findFragmentByTag("OrderListFragment")
        val orderCompletedFragment = supportFragmentManager.findFragmentByTag("OrderCompletedFragment")

        supportFragmentManager.beginTransaction()
                .add(R.id.contentContainer,
                        menuFragment,
                        "MenuFragment")
                .detach(categoryFragment)
                .detach(orderListFragment)
                .detach(orderCompletedFragment)
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

                    val categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
                    val menuFragment = supportFragmentManager.findFragmentByTag("MenuFragment")
                    val orderListFragment = OrderListFragment.newInstance(Parcels.wrap(menuList))
                    val orderCompletedFragment = supportFragmentManager.findFragmentByTag("OrderCompletedFragment")

                    supportFragmentManager.beginTransaction()
                            .add(R.id.contentContainer,
                                    orderListFragment,
                                    "OrderListFragment")
                            .detach(categoryFragment)
                            .detach(menuFragment)
                            .detach(orderCompletedFragment)
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

            val categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
            val menuFragment = supportFragmentManager.findFragmentByTag("MenuFragment")
            val orderListFragment = supportFragmentManager.findFragmentByTag("OrderListFragment")
            val orderCompletedFragment = supportFragmentManager.findFragmentByTag("OrderCompletedFragment")

            supportFragmentManager.beginTransaction()
                    .detach(categoryFragment)
                    .detach(menuFragment)
                    .detach(orderListFragment)
                    .attach(orderCompletedFragment)
                    .commit()
        }
    }

    @Subscribe
    fun onReciveClosed(eventSendClosed: EventSendClosed) {
        if (eventSendClosed.isClosed) {
            myToolbar.isShowBackIcon(false)

            val categoryFragment = supportFragmentManager.findFragmentByTag("CategoryFragment")
            val menuFragment = supportFragmentManager.findFragmentByTag("MenuFragment")
            val orderListFragment = supportFragmentManager.findFragmentByTag("OrderListFragment")
            val orderCompletedFragment = supportFragmentManager.findFragmentByTag("OrderCompletedFragment")

            supportFragmentManager.beginTransaction()
                    .attach(categoryFragment)
                    .detach(menuFragment)
                    .detach(orderListFragment)
                    .detach(orderCompletedFragment)
                    .commit()
        }
    }
}

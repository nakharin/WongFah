package com.nakharin.wongfah.controller.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nakharin.wongfah.R
import com.nakharin.wongfah.controller.fragment.MenuFragment
import com.nakharin.wongfah.controller.fragment.OrderListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer,
                            OrderListFragment.newInstance(),
                            "CategoryFragment")
                    .commit()
        }
    }

    private fun initToolbar() {
        myToolbar.isShowBackIcon(false)
        myToolbar.setItemCount(0)
    }
}

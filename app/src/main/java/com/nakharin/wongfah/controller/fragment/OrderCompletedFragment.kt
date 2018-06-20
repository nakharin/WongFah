package com.nakharin.wongfah.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nakharin.wongfah.R
import com.nakharin.wongfah.event.EventSendClosed
import com.nakharin.wongfah.event.EventSendPosition
import com.nakharin.wongfah.manager.bus.BusProvider
import com.nakharin.wongfah.utility.RecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_order_completed.*

class OrderCompletedFragment : Fragment() {

    companion object {
        fun newInstance(): OrderCompletedFragment {
            val fragment = OrderCompletedFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_order_completed, container, false)
        initInstances(rootView, savedInstanceState)
        return rootView
    }


    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnClose.setOnClickListener(onClickListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }

    /********************************************************************************************
     ************************************ Listener **********************************************
     ********************************************************************************************/

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        val eventSendClosed = EventSendClosed()
        eventSendClosed.isClosed = true
        BusProvider.getInstance().post(eventSendClosed)
    }
}
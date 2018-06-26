package com.nakharin.wongfah.controller.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nakharin.mylibrary.view.DialogLoadingFragment
import com.nakharin.wongfah.MyApplication
import com.nakharin.wongfah.R
import com.nakharin.wongfah.adapter.CategoryListAdapter
import com.nakharin.wongfah.addOnItemClickListener
import com.nakharin.wongfah.event.EventSendPosition
import com.nakharin.wongfah.manager.CategoryManager
import com.nakharin.wongfah.manager.bus.BusProvider
import com.nakharin.wongfah.network.APIService
import com.nakharin.wongfah.network.ConnectionService
import com.nakharin.wongfah.network.model.JsonCategory
import com.nakharin.wongfah.utility.RecyclerItemClickListener
import com.pawegio.kandroid.longToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryFragment : Fragment() {

    companion object {
        fun newInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerCategory: RecyclerView

    private lateinit var categoryListAdapter: CategoryListAdapter

    private var categoryList: ArrayList<JsonCategory> = arrayListOf()

    private lateinit var dialog: DialogLoadingFragment

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)

        init(savedInstanceState)

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_category, container, false)
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

        recyclerCategory = rootView.findViewById(R.id.recyclerCategory)
        val linearLayoutManager = LinearLayoutManager(rootView.context)
        recyclerCategory.layoutManager = linearLayoutManager

        categoryListAdapter = CategoryListAdapter()
        recyclerCategory.adapter = categoryListAdapter
    }

    private fun setUpView() {
        dialog = DialogLoadingFragment.newInstance(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Method from this class
        loadCategoryList()

        recyclerCategory.addOnItemClickListener(onItemClickListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }

    private fun loadCategoryList() {
        val categories = CategoryManager.getInstance().categoryList
        if (categories.isEmpty()) {
            dialog.show(fragmentManager, "dialog")

            val service = apiService.getCategoryList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        dialog.dismiss()
                        if (it.success) {
                            it.data?.let {
                                CategoryManager.getInstance().categoryList = it
                                categoryList.addAll(it)
                            }

                            categoryListAdapter.submitList(categoryList)

                        } else {
                            longToast(it.message)
                        }

                    }, {
                        dialog.dismiss()
                        longToast(it.localizedMessage)
                    })

            compositeDisposable.add(service)

        } else {
            categoryListAdapter.submitList(categories)
        }
    }

    /********************************************************************************************
     ************************************ Listener **********************************************
     ********************************************************************************************/

    private val onItemClickListener: RecyclerItemClickListener.OnClickListener = object : RecyclerItemClickListener.OnClickListener {
        override fun onItemClick(position: Int, view: View) {
            val eventSendPosition = EventSendPosition()
            eventSendPosition.position = position
            BusProvider.getInstance().post(eventSendPosition)
        }
    }
}
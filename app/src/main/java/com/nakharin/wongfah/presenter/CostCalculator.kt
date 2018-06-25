package com.nakharin.wongfah.presenter

import com.nakharin.wongfah.network.model.JsonMenu

class CostCalculator(private val menuList: ArrayList<JsonMenu>) : OnCalculateListener {

    private var netCost = 0.00
    private var vatCost = 0.00
    private var serviceChargeCost = 0.00
    private var totalCost = 0.00

    override fun onNetChanged(): Double {
        menuList.forEach {
            netCost += it.price
        }
        return netCost
    }

    override fun onVatChanged(): Double {
        vatCost = (netCost * 7) / 100
        return vatCost
    }

    override fun onServiceChargeChanged(): Double {
        serviceChargeCost = (netCost * 10) / 100
        return serviceChargeCost
    }

    override fun onTotalChanged(): Double {
        totalCost = netCost + vatCost + serviceChargeCost
        return totalCost
    }
}
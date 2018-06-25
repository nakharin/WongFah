package com.nakharin.wongfah.presenter

interface OnCalculateListener {
    fun onNetChanged() : Double
    fun onVatChanged() : Double
    fun onServiceChargeChanged() : Double
    fun onTotalChanged() : Double
}
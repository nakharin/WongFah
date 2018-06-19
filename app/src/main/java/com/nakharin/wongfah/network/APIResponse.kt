package com.nakharin.wongfah.network

import com.google.gson.annotations.SerializedName

class APIResponse<T> {

    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("message")
    val message: String = ""

    @SerializedName("categories")
    val data: T? = null
}
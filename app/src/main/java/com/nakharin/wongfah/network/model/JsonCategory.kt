package com.nakharin.wongfah.network.model

import com.google.gson.annotations.SerializedName

class JsonCategory {

    @SerializedName("id")
    var id: Int = -1

    @SerializedName("name")
    var name: String = ""

    @SerializedName("image_url")
    var imageUrl: String = ""

    @SerializedName("menu")
    var menus: ArrayList<JsonMenu> = arrayListOf()
}
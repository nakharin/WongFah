package com.nakharin.wongfah.network.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
class JsonMenu {

    @SerializedName("id")
    var id: Int = -1

    @SerializedName("name")
    var name: String = ""

    @SerializedName("image_url")
    var imageUrl: String = ""

    @SerializedName("price")
    var price: Int = 0
}
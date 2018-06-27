package com.nakharin.wongfah.network.model

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import com.nakharin.wongfah.utility.KParcelable
import com.nakharin.wongfah.utility.parcelableCreator

class JsonMenu() : KParcelable {

    @SerializedName("id")
    var id = -1

    @SerializedName("name")
    var name = ""

    @SerializedName("image_url")
    var imageUrl = ""

    @SerializedName("price")
    var price = 0

    constructor(p: Parcel) : this() {
        id = p.readInt()
        name = p.readString()
        imageUrl = p.readString()
        price = p.readInt()
    }

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(imageUrl)
        dest.writeInt(price)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::JsonMenu)
    }
}

package com.nakharin.wongfah.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class JsonMenu {

    JsonMenu() {
    }

    @SerializedName("id")
    public int id = -1;

    @SerializedName("name")
    public String name = "";

    @SerializedName("image_url")
    public String imageUrl = "";

    @SerializedName("price")
    public int price = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

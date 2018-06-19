package com.nakharin.wongfah.network

import com.nakharin.wongfah.network.model.JsonCategory
import io.reactivex.Observable
import retrofit2.http.GET

interface APIService {

    /********************************************************************************************
     *************************************** GET ************************************************
     ********************************************************************************************/

    @GET("menu_api.json")
    fun getCategoryList(): Observable<APIResponse<ArrayList<JsonCategory>>>
}
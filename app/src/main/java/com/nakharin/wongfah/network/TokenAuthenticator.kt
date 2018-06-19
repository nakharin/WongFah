package com.nakharin.wongfah.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

//https://stackoverflow.com/questions/22450036/refreshing-oauth-token-using-retrofit-without-modifying-all-calls

class TokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response?): Request? {
        // Refresh your access_token using a synchronous api request

        var requestResult: Request? = null

//        val responseRefreshToken: (APIResponse<JsonAuthenToken>) -> Unit = {
//            if (it.success) {
//                it.data.let {
//
//                    SharePrefUtility.accessToken = it!!.accessToken!!
//                    SharePrefUtility.refreshToken = it.refreshToken!!
//                    SharePrefUtility.expireDate = it.expireDate!!
//                    SharePrefUtility.expiresIn = it.expiresIn!!
//
//
//                    requestResult = response!!.request().newBuilder()
//                            .header(AUTHORIZATION, "Bearer ${SharePrefUtility.accessToken}")
//                            .build()
//                }
//
//            } else {
//                requestResult = null
//            }
//        }
//
//        val responseError: (Throwable) -> Unit = {
//            requestResult = null
//        }
//
//        val postRefreshToken = PostRefreshToken().apply {
//            refreshToken = SharePrefUtility.refreshToken
//            pushToken = "text"
//            deviceId = DeviceUtils.getInstance().deviceId
//            deviceOS = DeviceUtils.DEVICE_TYPE
//            osVersion = DeviceUtils.getInstance().deviceOSVersion
//            deviceName = DeviceUtils.getInstance().deviceName
//            deviceModel = DeviceUtils.getInstance().deviceModel
//        }
//
//        ConnectionService.getApiService().postRefreshToken(postRefreshToken)
//                .retryWhen(RetryWithDelay(2, 1000))
//                .subscribe(responseRefreshToken, responseError)

        // Add new header to rejected request and retry it

        return requestResult
    }
}
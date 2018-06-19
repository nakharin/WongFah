package com.nakharin.wongfah.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by ton on 6/22/2016 AD.
 */
class ConnectionService {

    companion object {

        private const val BASE_URL: String = "https://www.mellowlab.cloud/wongfah/"

        const val AUTHORIZATION: String = "Authorization"
        const val AUTHORIZATION_CODE: Int = 401

        private fun getRetrofit(): Retrofit {
            val gson = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .create()

            val httpClient = OkHttpClient.Builder()

//            if (SharePrefUtility.isLogin) {
//                val interceptor = Interceptor {
//                    val request = it.request().newBuilder()
//                            .addHeader(AUTHORIZATION, "Bearer ${SharePrefUtility.accessToken}")
//                            .build()
//                    return@Interceptor it.proceed(request)
//                }
//                httpClient.networkInterceptors().add(interceptor)
//            }

            httpClient.connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)

            // Set log
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)

            // Refresh your access_token using a synchronous api request
//            val tokenAuthenticator = TokenAuthenticator()
//            httpClient.authenticator(tokenAuthenticator)

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()
        }

        fun getApiService(): APIService {
            return getRetrofit().create(APIService::class.java)
        }
    }
}

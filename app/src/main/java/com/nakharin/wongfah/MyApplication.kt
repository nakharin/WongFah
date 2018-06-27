package com.nakharin.wongfah

import android.app.Application
import android.support.v7.widget.RecyclerView
import com.nakharin.wongfah.utility.RecyclerItemClickListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MyApplication : Application() {

    companion object {
        lateinit var instance: com.nakharin.wongfah.MyApplication
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Contextor.getInstance().init(instance)

//        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/PSL160pro.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        )

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(instance))
                .build()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
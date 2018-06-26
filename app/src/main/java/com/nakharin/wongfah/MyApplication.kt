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

/*************************************************************************************************
 *********************************** Extension Function ******************************************
 *************************************************************************************************/

fun String.toUnixTimeStamp(): Long {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale("TH", "th-TH"))
    val thaiPatten = this.split(" ")
    val engPatten = "${thaiPatten[0]} ${thaiPatten[1]} ${thaiPatten[2].toInt() - 543}"
    val dd = sdf.parse(engPatten)
    return dd.time / 1000
}

fun Date.toThaiPatten(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale("TH", "th-TH"))
    val s = formatter.format(this)
    val thaiPatten = s.split(" ")
    return "${thaiPatten[0]} ${thaiPatten[1]} ${thaiPatten[2].toInt() + 543}"
}

fun Date.toMonthYearThaiPatten(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale("TH", "th-TH"))
    val s = formatter.format(this)
    val thaiPatten = s.split(" ")
    return "${thaiPatten[1]} ${thaiPatten[2].toInt() + 543}"
}

fun Date.toFullThaiPatten(): String {
    val formatter = SimpleDateFormat("EEEE dd MMM yyyy HH:mm", Locale("TH", "th-TH"))
    val s = formatter.format(this)
    val thaiPatten = s.split(" ")
    return "${thaiPatten[0]} ${thaiPatten[1]} ${thaiPatten[2]} ${thaiPatten[3].toInt() + 543} ${thaiPatten[4]}"
}

fun Long.toDate(): Date? {
    val unixSeconds = this

    val nixDate = Date(unixSeconds * 1000L)

    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("GMT+7")

    val date = formatter.format(nixDate)

    return try {
        formatter.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun String.getTextDigits(): String {
    return this.replace("\\D+".toRegex(), "")
}

fun RecyclerView.addOnItemClickListener(listener: RecyclerItemClickListener.OnClickListener) {
    this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, listener, null))
}

fun RecyclerView.addOnItemClickListener(listener: RecyclerItemClickListener.OnLongClickListener) {
    this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, null, listener))
}

fun RecyclerView.addOnItemClickListener(onClick: RecyclerItemClickListener.OnClickListener, onLongClick: RecyclerItemClickListener.OnLongClickListener) {
    this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, onClick, onLongClick))
}
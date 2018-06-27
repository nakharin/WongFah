package com.nakharin.wongfah.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
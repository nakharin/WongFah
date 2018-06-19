package com.nakharin.wongfah.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer: JsonDeserializer<Date> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        val unixSeconds = json!!.asLong

        val nixDate = Date(unixSeconds*1000L)

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("TH", "th-TH"))
        formatter.timeZone = TimeZone.getTimeZone("GMT+7")

        val date = formatter.format(nixDate)

        return try {
            formatter.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}
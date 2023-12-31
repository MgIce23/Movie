package com.aps.movie.data.network.remote

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.util.*
import java.text.ParseException
import java.text.SimpleDateFormat

class DateJsonAdapter : JsonAdapter<Date>() {
    private companion object {
        @SuppressLint("SimpleDateFormat")
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd")

        @SuppressLint("SimpleDateFormat")
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        val dateString = reader.nextString()

        if (dateString.isEmpty()) {
            return null
        }

        val serverDateFormat = serverDateFormat.parseOrNull(dateString)
        if (serverDateFormat != null) {
            return serverDateFormat
        }

        val isoDate = isoDateFormat.parseOrNull(dateString)
        if (isoDate != null) {
            return isoDate
        }
        return null
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }
}
private fun SimpleDateFormat.parseOrNull(source: String): Date? {
    return try {
        parse(source)
    } catch (e: ParseException) {
        null
    } catch (e: ArrayIndexOutOfBoundsException) {
        null
    }
}

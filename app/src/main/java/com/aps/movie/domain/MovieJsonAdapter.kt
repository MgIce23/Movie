package com.aps.movie.domain

import com.aps.movie.data.modal.movie.MovieId
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieIdJsonAdapter {
    @ToJson
    fun toJson(value: MovieId): Int {
        return value.value
    }

    @FromJson
    fun fromJson(value: Int): MovieId {
        return MovieId(value = value)
    }
}

class LocalDateJsonAdapter {

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    @ToJson
    fun toJson(value: LocalDate): String {
        return value.format(formatter)
    }

    @FromJson
    fun fromJson(value: String): LocalDate {
        return LocalDate.parse(value, formatter)
    }
}
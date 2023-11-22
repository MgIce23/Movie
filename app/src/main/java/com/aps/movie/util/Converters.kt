package com.aps.movie.util

import androidx.room.TypeConverter
import com.aps.movie.data.modal.movie.MovieEntityType
import java.util.Date

class MovieEntityTypeConverters {
    @TypeConverter
    fun toMovieEntityType(value: String) = enumValueOf<MovieEntityType>(value)

    @TypeConverter
    fun fromMovieEntityType(value: MovieEntityType) = value.name
}

class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

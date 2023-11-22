package com.aps.movie.data.modal.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.aps.movie.data.modal.Presentable

@Entity(indices = [Index(value = ["type","language"])])
data class MovieEntity(
    override val id: Int,
    val type: MovieEntityType,
    override val title: String,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    val language: String

): Presentable {
    @PrimaryKey(autoGenerate = true)
    var entityId: Int = 0
}

enum class MovieEntityType{
    Discover, Upcoming, Trending, TopRated, Popular
}

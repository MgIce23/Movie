package com.aps.movie.domain


import com.aps.movie.data.modal.movie.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
){

    fun mapToMovie() = Movie(
        id = id,
        adult = adult,
        backdrop_path = "https://image.tmdb.org/t/p/original$backdropPath",
        genre_ids = genreIds,
        original_language = originalLanguage,
        original_title = originalTitle,
        overview = overview,
        popularity = popularity,
        poster_path = "https://image.tmdb.org/t/p/original$posterPath",
        release_date = releaseDate,
        title = title,
        video = video,
        vote_average = voteAverage,
        vote_count = voteCount,
    )
}
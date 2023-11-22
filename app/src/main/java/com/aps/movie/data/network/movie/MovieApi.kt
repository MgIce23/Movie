package com.aps.movie.data.network.movie

import com.aps.movie.data.modal.Config
import com.aps.movie.domain.BaseMovieResponse
import com.aps.movie.domain.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("configuration")
    fun getConfig(): Call<Config>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String,
        @Query("region") region: String
    ): BaseMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String,
        @Query("region") region: String
    ): BaseMovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") isoCode: String,
        @Query("region") region: String
    ): BaseMovieResponse

}
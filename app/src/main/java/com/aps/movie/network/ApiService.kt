package com.aps.movie.network

import com.aps.movie.domain.BaseMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int
    ): BaseMovieResponse

}
package com.aps.movie.data.network.movie

import com.aps.movie.data.modal.Config
import com.aps.movie.domain.BaseMovieResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieApiHelperImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieApiHelper {
    override fun getConfig(): Call<Config> {
        return movieApi.getConfig()
    }

    override suspend fun getPopularMovies(
        page: Int,
        isoCode: String,
        region: String
    ): BaseMovieResponse {
        return movieApi.getPopularMovies(
            page,
            isoCode,
            region
        )
    }

    override suspend fun getUpcomingMovies(
        page: Int,
        isoCode: String,
        region: String
    ): BaseMovieResponse {
        return movieApi.getUpcomingMovies(
            page,
            isoCode,
            region
        )
    }
    override suspend fun getNowPlayingMovies(
        page: Int,
        isoCode: String,
        region: String
    ): BaseMovieResponse {
       return movieApi.getNowPlayingMovies(
           page,
           isoCode,
           region
       )
    }
}
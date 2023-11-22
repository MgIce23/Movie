package com.aps.movie.data.network.movie

import com.aps.movie.data.modal.Config
import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.domain.BaseMovieResponse
import com.aps.movie.domain.MovieResponse
import retrofit2.Call

interface MovieApiHelper {

    fun getConfig(): Call<Config>

    suspend fun getPopularMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region
    ): BaseMovieResponse

    suspend fun getUpcomingMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region
    ) : BaseMovieResponse


    suspend fun getNowPlayingMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region
    ): BaseMovieResponse

}
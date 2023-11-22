package com.aps.movie.data.repo.movie

import androidx.paging.PagingData
import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.data.modal.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun popularMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieEntity>>

    fun upcomingMovie(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ) : Flow<PagingData<MovieEntity>>
}
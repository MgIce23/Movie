package com.aps.movie.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aps.movie.domain.MovieResponse
import com.aps.movie.data.paging.PopularMoviePagingSource
import com.aps.movie.data.paging.UpcomingMoviePagingSource
import com.aps.movie.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val apiService: ApiService
) {

    fun getPopularMovie(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                PopularMoviePagingSource(apiService)
            }
        ).flow
    }

    fun getUpComingMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                UpcomingMoviePagingSource(apiService)
            }
        ).flow
    }

}
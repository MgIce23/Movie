package com.aps.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aps.movie.domain.data.Movie
import com.aps.movie.domain.paging.PopularMoviePagingSource
import com.aps.movie.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    fun loadPopularViewModel(): Flow<PagingData<Movie>> {
        val moviePagingSource =
            PopularMoviePagingSource(apiService)
        return Pager(
            PagingConfig(pageSize = 20)
        ) {
            moviePagingSource
        }.flow.cachedIn(viewModelScope)

    }
}
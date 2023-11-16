package com.aps.movie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aps.movie.domain.MovieResponse
import com.aps.movie.domain.repo.Repository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {


    private val _loadPopularMovie = MutableStateFlow<PagingData<MovieResponse>>(PagingData.empty())
    val loadPopularMovie = _loadPopularMovie
    fun loadPopularMovie() = repository.getPopularMovie().cachedIn(viewModelScope)



}
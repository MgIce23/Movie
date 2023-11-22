package com.aps.movie.ui.screen.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.aps.movie.data.modal.Presentable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenUiState(
    val movieState: MovieState
){
    companion object {
        val default: MovieScreenUiState = MovieScreenUiState(
            movieState = MovieState.default,
        )
    }
}

@Stable
data class MovieState(
  val upComing: Flow<PagingData<Presentable>>
){
    companion object{
        val default = MovieState(
            upComing = emptyFlow()
        )
    }
}
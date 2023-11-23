package com.aps.movie.ui.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.domain.usecase.GetupcomingMovieUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val getPopularMovieUseCase : GetupcomingMovieUseCaseImpl
): ViewModel() {

    private val deviceLanguage: Flow<DeviceLanguage> = flowOf(DeviceLanguage.default)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val movieState = deviceLanguage.mapLatest { deviceLanguage ->
        MovieState(
            upComing = getPopularMovieUseCase(deviceLanguage).cachedIn(viewModelScope)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10),MovieState.default)


    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState : StateFlow<MovieScreenUiState> = movieState.mapLatest { movieState ->
        MovieScreenUiState(
            movieState = movieState
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly,MovieScreenUiState.default)


}
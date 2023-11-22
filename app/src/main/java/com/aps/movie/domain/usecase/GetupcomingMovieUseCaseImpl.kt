package com.aps.movie.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.data.modal.Presentable
import com.aps.movie.data.modal.movie.MovieEntity
import com.aps.movie.data.repo.movie.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetupcomingMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(deviceLanguage: DeviceLanguage): Flow<PagingData<Presentable>> {
        return repository.upcomingMovie(
            deviceLanguage
        ).mapLatest<PagingData<MovieEntity>, PagingData<Presentable>> { data ->
            data.map<MovieEntity, Presentable> {
                it
            }
        }
    }


}
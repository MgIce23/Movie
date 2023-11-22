package com.aps.movie.data.repo.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aps.movie.data.local.db.AppDatabase
import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.data.modal.movie.MovieEntity
import com.aps.movie.data.modal.movie.MovieEntityType
import com.aps.movie.data.network.movie.MovieApiHelper
import com.aps.movie.data.paging.movie.MovieRemotePagingMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val apiMovieApiHelper: MovieApiHelper,
    private val database: AppDatabase
) : MovieRepository {
    override fun popularMovies(deviceLanguage: DeviceLanguage): Flow<PagingData<MovieEntity>> {
        TODO("Not yet implemented")
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun upcomingMovie(deviceLanguage: DeviceLanguage): Flow<PagingData<MovieEntity>>  =
        Pager(
            config = PagingConfig(20),
            remoteMediator = MovieRemotePagingMediator(
                deviceLanguage = deviceLanguage,
                type = MovieEntityType.Upcoming,
                apiMovieApiHelper = apiMovieApiHelper,
                appDatabase = database
            ),
            pagingSourceFactory = {
                database.moviesDao().getAllMovies(
                    type = MovieEntityType.Upcoming,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(defaultDispatcher)

}
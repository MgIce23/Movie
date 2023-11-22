package com.aps.movie.data.paging.movie

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aps.movie.data.local.db.AppDatabase
import com.aps.movie.data.modal.DeviceLanguage
import com.aps.movie.data.modal.movie.MovieEntity
import com.aps.movie.data.modal.movie.MovieEntityType
import com.aps.movie.data.modal.movie.MoviesRemoteKeys
import com.aps.movie.data.network.movie.MovieApiHelper
import com.aps.movie.domain.BaseMovieResponse
import com.squareup.moshi.JsonDataException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MovieRemotePagingMediator(
    private val deviceLanguage: DeviceLanguage,
    private val type: MovieEntityType,
    private val apiMovieApiHelper: MovieApiHelper,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {

    private val movieCacheDao = appDatabase.moviesDao()
    private val movieRemoteKeyDao = appDatabase.movieRemoteKeyDao()


    private val apiMovieHelperMethod: suspend (Int, String, String) -> BaseMovieResponse =
        when (type) {
            MovieEntityType.Discover -> TODO()
            MovieEntityType.Upcoming -> apiMovieApiHelper::getUpcomingMovies
            MovieEntityType.Trending -> TODO()
            MovieEntityType.TopRated -> TODO()
            MovieEntityType.Popular -> apiMovieApiHelper::getPopularMovies
        }


    override suspend fun initialize(): InitializeAction {
        val remoteKey = appDatabase.withTransaction {
            movieRemoteKeyDao.getRemoteKey(
                type = type,
                language = deviceLanguage.languageCode
            )
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKey = appDatabase.withTransaction {
                        movieRemoteKeyDao.getRemoteKey(type, deviceLanguage.languageCode)
                    } ?: return MediatorResult.Success(true)
                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(true)
                    }
                    remoteKey.nextPage
                }
            }
            val result = apiMovieHelperMethod(page,deviceLanguage.languageCode,deviceLanguage.region)
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieCacheDao.deleteMoviesOfType(type, language = deviceLanguage.languageCode)
                }
                movieRemoteKeyDao.deleteRemoteKeyOfType(type, deviceLanguage.languageCode)

                val nextPage = if (result.results.isNotEmpty()) {
                    page + 1
                } else null
                val movieEntities = result.results.map { movie ->
                    MovieEntity(
                        id = movie.id,
                        type = type,
                        title = movie.title,
                        posterPath = movie.posterPath,
                        originalTitle = movie.originalTitle,
                        language = deviceLanguage.languageCode
                    )
                }
                movieRemoteKeyDao.insertKey(
                    MoviesRemoteKeys(
                        language = deviceLanguage.languageCode,
                        type = type,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                movieCacheDao.addMovies(movieEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = result.results.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: JsonDataException) {
            MediatorResult.Error(e)
        }
    }

}
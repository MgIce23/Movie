package com.aps.movie.initializer.di

import android.content.Context
import com.aps.movie.data.network.movie.MovieApiHelper
import com.aps.movie.data.paging.ConfigDataSource
import com.aps.movie.data.repo.movie.MovieRepository
import com.aps.movie.data.repo.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideConfigDataSource(
        @ApplicationContext context: Context,
        externalScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        apiMovieApiHelper: MovieApiHelper
    ) : ConfigDataSource = ConfigDataSource(
        context = context,
        externalScope = externalScope,
        defaultDispatcher = dispatcher,
        apiMovieApiHelper = apiMovieApiHelper
    )

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryBinds{
        @Binds
        @Singleton
        fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    }

}
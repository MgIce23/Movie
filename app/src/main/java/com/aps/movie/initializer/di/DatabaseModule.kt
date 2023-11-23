package com.aps.movie.initializer.di

import android.content.Context
import androidx.room.Room
import com.aps.movie.data.local.db.AppDatabase
import com.aps.movie.data.local.db.MovieDao
import com.aps.movie.data.local.db.MovieRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMoviesDao(database: AppDatabase): MovieDao = database.moviesDao()

    @Provides
    fun provideMovieRemoteKeysDao(database: AppDatabase): MovieRemoteKeyDao =
        database.movieRemoteKeyDao()

}
package com.aps.movie.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aps.movie.data.modal.movie.MovieEntity
import com.aps.movie.data.modal.movie.MoviesRemoteKeys

@Database(entities = [
    MovieEntity::class,
    MoviesRemoteKeys::class
                     ], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase(){

abstract fun moviesDao() : MovieDao

abstract fun movieRemoteKeyDao() : MovieRemoteKeyDao


}
package com.aps.movie.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.aps.movie.data.modal.movie.MovieEntity
import com.aps.movie.data.modal.movie.MovieEntityType
import com.aps.movie.util.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MovieDao {

    @Query("select * from MovieEntity where type=:type and language = :language")
    fun getAllMovies(type:MovieEntityType,language:String): PagingSource<Int,MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Query("delete from MovieEntity where type=:type and language = :language")
    suspend fun deleteMoviesOfType(type: MovieEntityType,language: String)
}
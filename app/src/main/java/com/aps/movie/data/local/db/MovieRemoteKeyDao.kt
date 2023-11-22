package com.aps.movie.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.aps.movie.data.modal.movie.MovieEntityType
import com.aps.movie.data.modal.movie.MoviesRemoteKeys
import com.aps.movie.util.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MovieRemoteKeyDao {

    @Query("select * from MoviesRemoteKeys where type=:type and language= :language")
    suspend fun getRemoteKey(type:MovieEntityType,language:String):MoviesRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKeys: MoviesRemoteKeys)

    @Query("delete from MoviesRemoteKeys where type = :type and language = :language")
    suspend fun deleteRemoteKeyOfType(type: MovieEntityType,language:String)
}
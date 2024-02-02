package com.mobiledevchtsca.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobiledevchtsca.movieapp.data.local.entity.MovieEntity
import com.mobiledevchtsca.movieapp.util.Columns
import com.mobiledevchtsca.movieapp.util.Tables
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${Tables.MOVIE_TABLE}")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM ${Tables.MOVIE_TABLE} WHERE ${Columns.MOVIE_ID_COLUMN} = :movieId")
    fun deleteMovie(movieId: Int?)
}
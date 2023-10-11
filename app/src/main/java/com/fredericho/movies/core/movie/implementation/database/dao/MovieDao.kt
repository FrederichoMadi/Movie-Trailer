package com.fredericho.movies.core.movie.implementation.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fredericho.movies.core.movie.implementation.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM tbl_movie")
    suspend fun getMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: MovieEntity)

    @Query("DELETE FROM tbl_movie WHERE id = :id")
    suspend fun removeFavorite(id: Int)

}
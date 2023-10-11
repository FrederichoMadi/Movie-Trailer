package com.fredericho.movies.core.movie.implementation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fredericho.movies.core.movie.implementation.database.dao.MovieDao
import com.fredericho.movies.core.movie.implementation.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
}
package com.rmd.business.moviesapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rmd.business.moviesapp.data.local.dao.MovieDao
import com.rmd.business.moviesapp.data.local.entity.MovieEntity


@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}
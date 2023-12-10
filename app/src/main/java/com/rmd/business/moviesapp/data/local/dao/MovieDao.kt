package com.rmd.business.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.rmd.business.moviesapp.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun getMovieListByCategory(category: String): List<MovieEntity>
}
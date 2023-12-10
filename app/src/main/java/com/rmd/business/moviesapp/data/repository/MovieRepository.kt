package com.rmd.business.moviesapp.data.repository

import com.rmd.business.moviesapp.domain.model.Movie
import com.rmd.business.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}
package com.rmd.business.moviesapp.data.remote


import com.rmd.business.moviesapp.BuildConfig
import com.rmd.business.moviesapp.data.dto.MovieDto
import com.rmd.business.moviesapp.data.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "API_KEY"
    ): MovieListDto

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = BuildConfig.BASE_URL
        const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL
    }
}
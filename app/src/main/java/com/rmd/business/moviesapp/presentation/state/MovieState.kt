package com.rmd.business.moviesapp.presentation.state

import com.rmd.business.moviesapp.domain.model.Movie

data class MovieState(
    val isLoading: Boolean = false,

    val popularMoviePage: Int = 1,
    val upcomingMoviePage: Int = 1,

    val isCurrentPopularScreen: Boolean = true,

    val popularMovie: List<Movie> = emptyList(),
    val upcomingMovie: List<Movie> = emptyList()
)
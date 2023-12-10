package com.rmd.business.moviesapp.presentation.state

import com.rmd.business.moviesapp.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
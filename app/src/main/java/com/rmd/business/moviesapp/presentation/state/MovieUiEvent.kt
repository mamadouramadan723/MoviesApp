package com.rmd.business.moviesapp.presentation.state

sealed interface MovieUiEvent {
    data class Paginate(val category: String) : MovieUiEvent
    data object Navigate : MovieUiEvent
}
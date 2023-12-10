package com.rmd.business.moviesapp.utils

sealed class Screen(val rout: String) {
    data object Home : Screen("main")
    data object PopularMovie : Screen("popularMovie")
    data object UpcomingMovie: Screen("upcomingMovie")
    data object Details : Screen("details")
}
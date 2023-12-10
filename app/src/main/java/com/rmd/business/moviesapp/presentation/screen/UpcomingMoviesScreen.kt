package com.rmd.business.moviesapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rmd.business.moviesapp.presentation.component.MovieItem
import com.rmd.business.moviesapp.presentation.state.MovieState
import com.rmd.business.moviesapp.presentation.state.MovieUiEvent
import com.rmd.business.moviesapp.utils.Category

@Composable
fun UpcomingMoviesScreen(
    movieState: MovieState,
    navController: NavHostController,
    onEvent: (MovieUiEvent) -> Unit
) {

    if (movieState.upcomingMovie.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(movieState.upcomingMovie.size) { index ->
                MovieItem(
                    movie = movieState.upcomingMovie[index],
                    navHostController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (index >= movieState.upcomingMovie.size - 1 && !movieState.isLoading) {
                    onEvent(MovieUiEvent.Paginate(Category.UPCOMING))
                }

            }
        }
    }

}
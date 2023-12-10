package com.rmd.business.moviesapp.presentation.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rmd.business.moviesapp.R
import com.rmd.business.moviesapp.presentation.state.MovieUiEvent
import com.rmd.business.moviesapp.presentation.viewmodel.MovieViewModel
import com.rmd.business.moviesapp.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val movieViewModel = hiltViewModel<MovieViewModel>()
    val movieState = movieViewModel.movieState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigationBar(
            bottomNavController = bottomNavController, onEvent = movieViewModel::onEvent
        )
    }, topBar = {
        TopAppBar(
            title = {
                Text(
                    text = if (movieState.isCurrentPopularScreen)
                        stringResource(R.string.popular_movies)
                    else
                        stringResource(R.string.upcoming_movies),
                    fontSize = 20.sp
                )
            },
            modifier = Modifier.shadow(2.dp),
            colors = TopAppBarDefaults.smallTopAppBarColors(
                MaterialTheme.colorScheme.inverseOnSurface
            )
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovie.rout
            ) {
                composable(Screen.PopularMovie.rout) {
                    PopularMoviesScreen(
                        navController = navController,
                        movieState = movieState,
                        onEvent = movieViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovie.rout) {
                    UpcomingMoviesScreen(
                        navController = navController,
                        movieState = movieState,
                        onEvent = movieViewModel::onEvent
                    )
                }
            }
        }
    }

}


@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onEvent: (MovieUiEvent) -> Unit
) {

    val items = listOf(
        BottomItem(
            title = stringResource(R.string.popular),
            icon = Icons.Rounded.Movie
        ), BottomItem(
            title = stringResource(R.string.upcoming),
            icon = Icons.Rounded.Upcoming
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {
                            onEvent(MovieUiEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovie.rout)
                        }

                        1 -> {
                            onEvent(MovieUiEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.UpcomingMovie.rout)
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }

}

data class BottomItem(
    val title: String, val icon: ImageVector
)
package com.rmd.business.moviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmd.business.moviesapp.data.repository.MovieRepository
import com.rmd.business.moviesapp.utils.Category
import com.rmd.business.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var _movieState = MutableStateFlow(MovieState())
    val movieState = _movieState.asStateFlow()

    init {
        getPopularMovie(false)
        getUpcomingMovie(false)
    }

    fun onEvent(event: MovieUiEvent) {
        when (event) {
            MovieUiEvent.Navigate -> {
                _movieState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovie(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovie(true)
                }
            }
        }
    }

    private fun getPopularMovie(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getMovie(
                forceFetchFromRemote,
                Category.POPULAR,
                movieState.value.popularMoviePage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieState.update {
                                it.copy(
                                    popularMovie = movieState.value.popularMovie
                                            + popularList.shuffled(),
                                    popularMoviePage = movieState.value.popularMoviePage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    private fun getUpcomingMovie(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getMovie(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieState.value.upcomingMoviePage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingList ->
                            _movieState.update {
                                it.copy(
                                    upcomingMovie = movieState.value.upcomingMovie
                                            + upcomingList.shuffled(),
                                    upcomingMoviePage = movieState.value.upcomingMoviePage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _movieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

}
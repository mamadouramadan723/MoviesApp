package com.rmd.business.moviesapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmd.business.moviesapp.data.repository.MovieRepository
import com.rmd.business.moviesapp.presentation.state.DetailsState
import com.rmd.business.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")

    private var _detialsState = MutableStateFlow(DetailsState())
    val detailsState = _detialsState.asStateFlow()

    init {
        getMovie(movieId ?: -1)
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            _detialsState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _detialsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _detialsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { movie ->
                            _detialsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }
}
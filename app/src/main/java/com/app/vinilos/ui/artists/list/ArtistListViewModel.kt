package com.app.vinilos.ui.artists.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.vinilos.data.model.Artist
import com.app.vinilos.data.repository.ArtistRepository
import com.app.vinilos.ui.common.UiState
import kotlinx.coroutines.launch

class ArtistsListViewModel(
    private val repository: ArtistRepository
) : ViewModel() {

    private val _state = MutableLiveData<UiState<List<Artist>>>(UiState.Loading)
    val state: LiveData<UiState<List<Artist>>> = _state

    init { loadArtists() }

    fun loadArtists() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val artists = repository.fetchArtists()
                _state.value = UiState.Success(artists)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(private val repository: ArtistRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ArtistsListViewModel(repository) as T
    }
}
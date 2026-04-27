package com.app.vinilos.ui.artists.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.vinilos.data.model.Artist
import com.app.vinilos.data.repository.ArtistRepository
import com.app.vinilos.ui.common.UiState
import kotlinx.coroutines.launch

class ArtistDetailViewModel(
    private val repository: ArtistRepository,
    private val artistId: Int
) : ViewModel() {

    private val _state = MutableLiveData<UiState<Artist>>(UiState.Loading)
    val state: LiveData<UiState<Artist>> = _state

    init { loadArtist() }

    fun loadArtist() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val artist = repository.fetchArtist(artistId)
                _state.value = UiState.Success(artist)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(
        private val repository: ArtistRepository,
        private val artistId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ArtistDetailViewModel(repository, artistId) as T
    }
}
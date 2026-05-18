package com.app.vinilos.ui.albums.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.vinilos.data.model.Album
import com.app.vinilos.data.repository.AlbumRepository
import com.app.vinilos.ui.common.UiState
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val repository: AlbumRepository,
    albumId: Int
) : ViewModel() {

    private val _state = MutableLiveData<UiState<Album>>(UiState.Loading)
    val state: LiveData<UiState<Album>> = _state

    init { loadAlbum(albumId)
    }

    fun loadAlbum(albumId: Int) {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val album = repository.fetchAlbum(albumId)
                _state.value = UiState.Success(album)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(
        private val repository: AlbumRepository,
        private val albumId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            AlbumDetailViewModel(repository, albumId) as T
    }
}
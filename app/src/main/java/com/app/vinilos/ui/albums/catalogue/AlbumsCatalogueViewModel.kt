package com.app.vinilos.ui.albums.catalogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.vinilos.data.model.Album
import com.app.vinilos.data.repository.AlbumRepository
import com.app.vinilos.ui.common.UiState
import kotlinx.coroutines.launch

class AlbumsCatalogueViewModel(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _state = MutableLiveData<UiState<List<Album>>>(UiState.Loading)
    val state: LiveData<UiState<List<Album>>> = _state

    init { loadAlbums() }

    fun loadAlbums() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val albums = repository.fetchAlbums()
                _state.value = UiState.Success(albums)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    class Factory(private val repository: AlbumRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            AlbumsCatalogueViewModel(repository) as T
    }
}
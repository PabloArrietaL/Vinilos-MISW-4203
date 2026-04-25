package com.app.vinilos.data.repository

import com.app.vinilos.data.model.Album
import com.app.vinilos.data.remote.AlbumApiService

class AlbumRepository(
    private val api: AlbumApiService
) {
    suspend fun fetchAlbums(): List<Album> = api.getAlbums()
    suspend fun fetchAlbum(id: Int): Album = api.getAlbum(id)
}
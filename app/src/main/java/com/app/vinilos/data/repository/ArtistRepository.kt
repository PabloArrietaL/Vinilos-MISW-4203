package com.app.vinilos.data.repository

import com.app.vinilos.data.model.Artist
import com.app.vinilos.data.remote.ArtistApiService

class ArtistRepository(
    private val api: ArtistApiService
) {
    suspend fun fetchArtists(): List<Artist> = api.getArtists()
    suspend fun fetchArtist(id: Int): Artist = api.getArtist(id)
}
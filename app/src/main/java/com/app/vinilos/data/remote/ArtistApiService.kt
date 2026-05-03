package com.app.vinilos.data.remote

import com.app.vinilos.data.model.Artist
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistApiService {
    @GET("musicians")
    suspend fun getArtists(): List<Artist>

    @GET("musicians/{id}")
    suspend fun getArtist(@Path("id") id: Int): Artist
}
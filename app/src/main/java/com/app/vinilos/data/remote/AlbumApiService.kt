package com.app.vinilos.data.remote

import com.app.vinilos.data.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Int): Album
}
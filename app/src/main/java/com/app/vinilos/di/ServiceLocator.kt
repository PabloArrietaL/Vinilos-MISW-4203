package com.app.vinilos.di

import com.app.vinilos.data.remote.RetrofitClient
import com.app.vinilos.data.repository.AlbumRepository
import com.app.vinilos.data.repository.ArtistRepository

object ServiceLocator {
    val albumRepository: AlbumRepository by lazy {
        AlbumRepository(RetrofitClient.albumApi)
    }

    val artistRepository: ArtistRepository by lazy {
        ArtistRepository(RetrofitClient.artistApi)
    }
}
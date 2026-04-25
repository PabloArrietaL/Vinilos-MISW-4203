package com.app.vinilos.di

import com.app.vinilos.data.remote.RetrofitClient
import com.app.vinilos.data.repository.AlbumRepository

object ServiceLocator {
    val albumRepository: AlbumRepository by lazy {
        AlbumRepository(RetrofitClient.albumApi)
    }
}
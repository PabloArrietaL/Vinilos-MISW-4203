package com.app.vinilos.data.model

import com.squareup.moshi.Json

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String?,
    val genre: String?,
    val recordLabel: String?,
    val tracks: List<Track> = emptyList(),
    val performers: List<Performer> = emptyList()
)

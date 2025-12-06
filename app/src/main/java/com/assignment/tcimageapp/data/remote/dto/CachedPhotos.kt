package com.assignment.tcimageapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CachedPhotos (
    val photos: List<PhotoDto> = emptyList()
)
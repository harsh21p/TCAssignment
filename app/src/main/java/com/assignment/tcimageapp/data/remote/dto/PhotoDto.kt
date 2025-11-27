package com.assignment.tcimageapp.data.remote.dto

import com.squareup.moshi.Json

/**
 * Data model received directly from the API.
 *
 * This DTO is used across the entire app.
 */
data class PhotoDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @Json(name = "download_url")
    val downloadUrl: String
)
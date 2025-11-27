package com.assignment.tcimageapp.data.remote.dto

import com.squareup.moshi.Json

data class PhotoDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @Json(name = "download_url")
    val downloadUrl: String
)
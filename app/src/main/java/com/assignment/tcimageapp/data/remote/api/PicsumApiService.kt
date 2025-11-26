package com.assignment.tcimageapp.data.remote.api

import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import retrofit2.http.GET

interface PicsumApiService {

    @GET("v2/list")
    suspend fun getPhotos(): List<PhotoDto>
}

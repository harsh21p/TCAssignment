package com.assignment.tcimageapp.data.remote.api

import retrofit2.http.GET
import com.assignment.tcimageapp.data.remote.dto.PhotoDto

interface PicsumApiService {

    @GET("v2/list")
    suspend fun getPhotos(): List<PhotoDto>
}

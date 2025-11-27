package com.assignment.tcimageapp.data.remote.api

import retrofit2.http.GET
import com.assignment.tcimageapp.data.remote.dto.PhotoDto

/**
 * Retrofit service for calling the API.
 *
 * Endpoints:
 *  - /v2/list -> retrieves list of all photos
 */
interface PicsumApiService {

    @GET("v2/list")
    suspend fun getPhotos(): List<PhotoDto>
}

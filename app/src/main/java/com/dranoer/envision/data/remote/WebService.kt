package com.dranoer.envision.data.remote

import com.dranoer.envision.data.model.OCRModel
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface WebService {

    @Multipart
    @POST("readDocument")
    suspend fun postPhoto(
        @Header("Cookie") Cookie: String,
        @Part photo: MultipartBody.Part
    ): OCRModel
}
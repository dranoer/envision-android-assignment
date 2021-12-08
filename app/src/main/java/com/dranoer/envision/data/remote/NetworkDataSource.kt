package com.dranoer.envision.data.remote

import com.dranoer.envision.data.model.OCRModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MultipartBody
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(
    private val webService: WebService
) {

    suspend fun postPhoto(cookie: String, photo: MultipartBody.Part): Resource<OCRModel> {
        return Resource.Success(webService.postPhoto(cookie, photo))
    }
}
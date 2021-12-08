package com.dranoer.envision.domain

import com.dranoer.envision.data.model.OCRModel
import com.dranoer.envision.data.remote.NetworkDataSource
import com.dranoer.envision.data.remote.Resource
import okhttp3.MultipartBody
import javax.inject.Inject

class OcrRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) {

    suspend fun postPhoto(cookie: String, photo: MultipartBody.Part): Resource<OCRModel> {
        return networkDataSource.postPhoto(cookie, photo)
    }
}
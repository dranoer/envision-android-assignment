package com.dranoer.envision.ui

import androidx.lifecycle.*
import com.dranoer.envision.Constants
import com.dranoer.envision.data.model.OcrEntity
import com.dranoer.envision.data.remote.Resource
import com.dranoer.envision.domain.OcrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    var repository: OcrRepository
) : ViewModel() {

    val ocrs: LiveData<List<OcrEntity>> = repository.ocrs.asLiveData()

    fun postPhoto(photo: File) = liveData(Dispatchers.IO) {
        val filePart = MultipartBody.Part.createFormData(
            "photo",
            photo.name,
            photo.asRequestBody("image/*".toMediaTypeOrNull())
        )

        emit(Resource.Loading())
        try {
            emit(
                repository.postPhoto(
                    Constants.COOKIE,
                    filePart
                )
            )
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun saveParagraph(paragraph: String) {
        viewModelScope.launch {
            repository.saveOcr(paragraph)
        }
    }
}
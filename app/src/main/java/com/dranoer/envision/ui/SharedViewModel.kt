package com.dranoer.envision.ui

import androidx.lifecycle.*
import com.dranoer.envision.data.model.OcrEntity
import com.dranoer.envision.data.remote.Resource
import com.dranoer.envision.domain.OcrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _paragraphsLiveData = MutableLiveData<String>()
    val paragraphsLiveData: LiveData<String> = _paragraphsLiveData

    val ocrs: LiveData<List<OcrEntity>> = repository.ocrs.asLiveData()

    fun postPhoto(photo: File) {

        val filePart = MultipartBody.Part.createFormData(
            "photo", photo.name, photo
                .asRequestBody("image/*".toMediaTypeOrNull())
        )

        viewModelScope.launch {
            val result = repository.postPhoto(
                "__cfduid=d97604b6c67574ccd048c013ffbee703a1614774197",
                filePart
            )
            when (result) {
                is Resource.Success -> {

                    // Fill the data that is required in captured screen
                    var text = ""
                    for (paragraph in result.data.response.paragraphs) {
                        text += paragraph.paragraph
                    }
                    _paragraphsLiveData.value = text
                }
                is Resource.Failure -> {
                }
            }
        }
    }

    fun saveParagraph(paragraph: String) {
        viewModelScope.launch {
            repository.saveOcr(paragraph)

        }
    }
}
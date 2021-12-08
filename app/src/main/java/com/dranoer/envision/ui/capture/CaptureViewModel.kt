package com.dranoer.envision.ui.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dranoer.envision.data.model.OCRModel
import com.dranoer.envision.data.remote.Resource
import com.dranoer.envision.domain.OCRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    var repository: OCRRepository
) : ViewModel() {

    private val _ocrModel = MutableLiveData<OCRModel>()
    val ocrModel: LiveData<OCRModel> = _ocrModel

    private val _hasFinished = MutableLiveData<Boolean>()
    val hasFinished: LiveData<Boolean> = _hasFinished

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
                    _ocrModel.value = result.data!!
                    _hasFinished.value = true
                }
                is Resource.Failure -> {
                }
            }

        }
    }
}
package com.dranoer.envision.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dranoer.envision.data.model.OcrEntity
import com.dranoer.envision.domain.OcrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    var ocrRepository: OcrRepository
) : ViewModel() {

    val ocrs: LiveData<List<OcrEntity>> = ocrRepository.ocrs.asLiveData()
}
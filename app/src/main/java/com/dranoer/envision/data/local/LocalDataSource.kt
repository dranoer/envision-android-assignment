package com.dranoer.envision.data.local

import com.dranoer.envision.Constants.DATE_FORMAT
import com.dranoer.envision.data.model.OcrEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val ocrDao: OcrDao) {

    val ocrs: Flow<List<OcrEntity>> = ocrDao.getOcrs()

    suspend fun saveOcr(paragraph: String) {

        val ocr = OcrEntity(
            id = 0,
            date = SimpleDateFormat(DATE_FORMAT, Locale.US).format(System.currentTimeMillis()),
            paragraph = paragraph
        )
        ocrDao.saveOcr(ocr)
    }
}
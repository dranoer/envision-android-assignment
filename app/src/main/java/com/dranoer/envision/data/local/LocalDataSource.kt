package com.dranoer.envision.data.local

import com.dranoer.envision.data.model.OcrEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val ocrDao: OcrDao) {

    val ocrs: Flow<List<OcrEntity>> = ocrDao.getOcrs()
}
package com.dranoer.envision.data.local

import androidx.room.Dao
import androidx.room.Query
import com.dranoer.envision.data.model.OcrEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OcrDao {

    @Query("SELECT * FROM ocr_table ORDER BY date DESC")
    fun getOcrs(): Flow<List<OcrEntity>>
}
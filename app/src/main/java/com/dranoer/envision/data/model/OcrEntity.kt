package com.dranoer.envision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ocr_table")
data class OcrEntity(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: String,
    val paragraph: String,
)
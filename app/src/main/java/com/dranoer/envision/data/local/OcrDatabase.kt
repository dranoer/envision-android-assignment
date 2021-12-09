package com.dranoer.envision.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dranoer.envision.data.model.OcrEntity

@Database(entities = [OcrEntity::class], version = 1, exportSchema = false)
abstract class OcrDatabase : RoomDatabase() {

    abstract fun ocrDao(): OcrDao
}
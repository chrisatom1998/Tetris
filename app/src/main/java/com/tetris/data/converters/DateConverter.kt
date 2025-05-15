package com.tetris.data.converters

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter for Room database to convert between Date objects and Long timestamps.
 * This is necessary because Room cannot directly store objects like Date.
 */
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
package com.tetris.data.converters

import androidx.room.TypeConverter

/**
 * Type converter for Room to store and retrieve IntArray.
 * Converts IntArray to a comma-separated String and vice-versa.
 */
object IntArrayConverter {
    @TypeConverter
    @JvmStatic // Important for Room to discover these methods in an object
    fun fromIntArray(value: IntArray?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    @JvmStatic // Important for Room to discover these methods in an object
    fun toIntArray(value: String?): IntArray? {
        return value?.split(',')
            ?.filter { it.isNotEmpty() } // Handle potential empty strings if the source string ends with a comma or has consecutive commas
            ?.mapNotNull { it.toIntOrNull() }
            ?.toIntArray()
    }
}
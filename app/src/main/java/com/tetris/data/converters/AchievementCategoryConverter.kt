package com.tetris.data.converters

import androidx.room.TypeConverter
import com.tetris.model.AchievementCategory

/**
 * Type converter for Room to store and retrieve AchievementCategory enum.
 * Converts AchievementCategory to its String name and vice-versa.
 */
object AchievementCategoryConverter {
    @TypeConverter
    @JvmStatic
    fun fromAchievementCategory(value: AchievementCategory?): String? {
        return value?.name
    }

    @TypeConverter
    @JvmStatic
    fun toAchievementCategory(value: String?): AchievementCategory? {
        return value?.let { enumValueOf<AchievementCategory>(it) }
    }
}
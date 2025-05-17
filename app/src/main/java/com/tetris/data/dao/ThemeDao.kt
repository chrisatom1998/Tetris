package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Theme
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the Theme entity.
 */
@Dao
interface ThemeDao {
    @Insert
    suspend fun insert(theme: Theme): Long

    @Update
    suspend fun update(theme: Theme) // Reverted to Unit

    @Delete
    suspend fun delete(theme: Theme) // Reverted to Unit

    @Query("SELECT * FROM themes WHERE id = :id")
    suspend fun getThemeById(id: Int): Theme?

    @Query("SELECT * FROM themes WHERE name = :name")
    suspend fun getThemeByName(name: String): Theme?

    @Query("SELECT * FROM themes")
    fun getAllThemes(): Flow<List<Theme>>

    @Query("SELECT * FROM themes WHERE isUnlocked = 1")
    fun getUnlockedThemes(): Flow<List<Theme>>

    @Query("SELECT * FROM themes WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveTheme(): Theme?

    @Transaction
    suspend fun setActiveTheme(themeId: Int) {
        deactivateAllThemes()
        activateTheme(themeId)
    }

    @Query("UPDATE themes SET isActive = 0")
    suspend fun deactivateAllThemes() // Reverted to Unit

    @Query("UPDATE themes SET isActive = 1 WHERE id = :themeId")
    suspend fun activateTheme(themeId: Int) // Reverted to Unit

    @Query("UPDATE themes SET isUnlocked = 1 WHERE id = :themeId")
    suspend fun unlockTheme(themeId: Int) // Reverted to Unit

    @Query("SELECT isUnlocked FROM themes WHERE id = :themeId")
    suspend fun isThemeUnlocked(themeId: Int): Boolean

    @Query("SELECT COUNT(*) FROM themes")
    suspend fun getThemeCount(): Int
}
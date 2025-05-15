package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Theme
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the Theme entity.
 * Contains methods for accessing and manipulating theme data in the database.
 */
@Dao
interface ThemeDao {
    /**
     * Insert a new theme
     */
    @Insert
    suspend fun insert(theme: Theme): Long
    
    /**
     * Update an existing theme
     */
    @Update
    suspend fun update(theme: Theme)
    
    /**
     * Delete a theme
     */
    @Delete
    suspend fun delete(theme: Theme)
    
    /**
     * Get a theme by ID
     */
    @Query("SELECT * FROM themes WHERE id = :id")
    suspend fun getThemeById(id: Int): Theme?
    
    /**
     * Get a theme by name
     */
    @Query("SELECT * FROM themes WHERE name = :name")
    suspend fun getThemeByName(name: String): Theme?
    
    /**
     * Get all themes
     */
    @Query("SELECT * FROM themes")
    fun getAllThemes(): Flow<List<Theme>>
    
    /**
     * Get all unlocked themes
     */
    @Query("SELECT * FROM themes WHERE isUnlocked = 1")
    fun getUnlockedThemes(): Flow<List<Theme>>
    
    /**
     * Get the active theme
     */
    @Query("SELECT * FROM themes WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveTheme(): Theme?
    
    /**
     * Set a theme as active and deactivate all others
     */
    @Transaction
    suspend fun setActiveTheme(themeId: Int) {
        // Deactivate all themes
        deactivateAllThemes()
        
        // Activate the specified theme
        activateTheme(themeId)
    }
    
    /**
     * Deactivate all themes
     */
    @Query("UPDATE themes SET isActive = 0")
    suspend fun deactivateAllThemes()
    
    /**
     * Activate a specific theme
     */
    @Query("UPDATE themes SET isActive = 1 WHERE id = :themeId")
    suspend fun activateTheme(themeId: Int)
    
    /**
     * Unlock a theme
     */
    @Query("UPDATE themes SET isUnlocked = 1 WHERE id = :themeId")
    suspend fun unlockTheme(themeId: Int)
    
    /**
     * Check if a theme is unlocked
     */
    @Query("SELECT isUnlocked FROM themes WHERE id = :themeId")
    suspend fun isThemeUnlocked(themeId: Int): Boolean
    
    /**
     * Check if the database needs initialization with default themes
     */
    @Query("SELECT COUNT(*) FROM themes")
    suspend fun getThemeCount(): Int
}
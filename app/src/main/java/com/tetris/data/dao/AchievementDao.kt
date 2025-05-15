package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Achievement
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * DAO (Data Access Object) for the Achievement entity.
 * Contains methods for accessing and manipulating achievement data in the database.
 */
@Dao
interface AchievementDao {
    /**
     * Insert a new achievement
     */
    @Insert
    suspend fun insert(achievement: Achievement): Long
    
    /**
     * Update an existing achievement
     */
    @Update
    suspend fun update(achievement: Achievement)
    
    /**
     * Delete an achievement
     */
    @Delete
    suspend fun delete(achievement: Achievement)
    
    /**
     * Get an achievement by ID
     */
    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getAchievementById(id: Int): Achievement?
    
    /**
     * Get an achievement by achievement type ID
     */
    @Query("SELECT * FROM achievements WHERE playerId = :playerId AND achievementId = :achievementId")
    suspend fun getPlayerAchievement(playerId: Int, achievementId: String): Achievement?
    
    /**
     * Get all achievements for a player
     */
    @Query("SELECT * FROM achievements WHERE playerId = :playerId")
    fun getPlayerAchievements(playerId: Int): Flow<List<Achievement>>
    
    /**
     * Get all unlocked achievements for a player
     */
    @Query("SELECT * FROM achievements WHERE playerId = :playerId AND isUnlocked = 1")
    fun getUnlockedAchievements(playerId: Int): Flow<List<Achievement>>
    
    /**
     * Get all locked achievements for a player
     */
    @Query("SELECT * FROM achievements WHERE playerId = :playerId AND isUnlocked = 0")
    fun getLockedAchievements(playerId: Int): Flow<List<Achievement>>
    
    /**
     * Count unlocked achievements for a player
     */
    @Query("SELECT COUNT(*) FROM achievements WHERE playerId = :playerId AND isUnlocked = 1")
    suspend fun countUnlockedAchievements(playerId: Int): Int
    
    /**
     * Unlock an achievement for a player
     */
    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :date WHERE playerId = :playerId AND achievementId = :achievementId AND isUnlocked = 0")
    suspend fun unlockAchievement(playerId: Int, achievementId: String, date: Date = Date()): Int
    
    /**
     * Check if player has a specific achievement
     */
    @Query("SELECT isUnlocked FROM achievements WHERE playerId = :playerId AND achievementId = :achievementId")
    suspend fun hasAchievement(playerId: Int, achievementId: String): Boolean?
    
    /**
     * Get recently unlocked achievements
     */
    @Query("SELECT * FROM achievements WHERE playerId = :playerId AND isUnlocked = 1 " +
           "ORDER BY unlockedAt DESC LIMIT :limit")
    suspend fun getRecentAchievements(playerId: Int, limit: Int): List<Achievement>
    
    /**
     * Get achievement progress data for a player - joins with achievement definitions
     */
    @Query("SELECT a.id, a.achievementId, a.playerId, a.isUnlocked, a.unlockedAt, " +
           "ad.name, ad.description, ad.progressRequired, ad.iconResource " +
           "FROM achievements a JOIN achievement_definitions ad ON a.achievementId = ad.id " +
           "WHERE a.playerId = :playerId")
    fun getAchievementProgressData(playerId: Int): Flow<List<AchievementProgress>>
    
    /**
     * Ensure all achievements exist for player
     */
    @Transaction
    suspend fun ensurePlayerHasAchievements(playerId: Int, achievementIds: List<String>) {
        achievementIds.forEach { achievementId ->
            val hasAchievement = getPlayerAchievement(playerId, achievementId) != null
            
            if (!hasAchievement) {
                // Create a new achievement entry for the player
                val achievement = Achievement(
                    playerId = playerId,
                    achievementId = achievementId,
                    isUnlocked = false
                )
                insert(achievement)
            }
        }
    }
    
    /**
     * Track achievement progress and unlock if target reached
     */
    @Transaction
    suspend fun trackAchievementProgress(
        playerId: Int,
        achievementId: String,
        currentProgress: Int,
        targetProgress: Int
    ): Boolean {
        if (currentProgress >= targetProgress) {
            // Unlock achievement if it meets the required progress
            val rowsAffected = unlockAchievement(playerId, achievementId)
            return rowsAffected > 0
        }
        return false
    }
    
    /**
     * Check achievements based on player stats
     */
    @Transaction
    suspend fun checkAchievementsBasedOnStats(
        playerId: Int,
        totalGames: Int,
        highScore: Long,
        linesCleared: Int,
        level: Int,
        tetrisCount: Int
    ): List<String> {
        val unlockedAchievements = mutableListOf<String>()
        
        // Check game count achievements
        if (totalGames >= 1) {
            if (unlockAchievement(playerId, "games_1") > 0) {
                unlockedAchievements.add("games_1")
            }
        }
        if (totalGames >= 10) {
            if (unlockAchievement(playerId, "games_10") > 0) {
                unlockedAchievements.add("games_10")
            }
        }
        if (totalGames >= 50) {
            if (unlockAchievement(playerId, "games_50") > 0) {
                unlockedAchievements.add("games_50")
            }
        }
        
        // Check high score achievements
        if (highScore >= 10000) {
            if (unlockAchievement(playerId, "score_10k") > 0) {
                unlockedAchievements.add("score_10k")
            }
        }
        if (highScore >= 50000) {
            if (unlockAchievement(playerId, "score_50k") > 0) {
                unlockedAchievements.add("score_50k")
            }
        }
        
        // Check lines cleared achievements
        if (linesCleared >= 100) {
            if (unlockAchievement(playerId, "lines_100") > 0) {
                unlockedAchievements.add("lines_100")
            }
        }
        if (linesCleared >= 1000) {
            if (unlockAchievement(playerId, "lines_1k") > 0) {
                unlockedAchievements.add("lines_1k")
            }
        }
        
        // Check level achievements
        if (level >= 10) {
            if (unlockAchievement(playerId, "level_10") > 0) {
                unlockedAchievements.add("level_10")
            }
        }
        
        // Check tetris achievements
        if (tetrisCount >= 1) {
            if (unlockAchievement(playerId, "first_tetris") > 0) {
                unlockedAchievements.add("first_tetris")
            }
        }
        if (tetrisCount >= 10) {
            if (unlockAchievement(playerId, "tetris_10") > 0) {
                unlockedAchievements.add("tetris_10")
            }
        }
        
        return unlockedAchievements
    }
    
    /**
     * Data class for achievement progress data
     */
    data class AchievementProgress(
        val id: Int,
        val achievementId: String,
        val playerId: Int,
        val isUnlocked: Boolean,
        val unlockedAt: Date?,
        val name: String,
        val description: String,
        val progressRequired: Int,
        val iconResource: String
    )
}
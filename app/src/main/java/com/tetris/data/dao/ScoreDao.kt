package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.HighScore
import com.tetris.model.GameMode
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * DAO (Data Access Object) for the HighScore entity.
 * Contains methods for accessing and manipulating high score data in the database.
 */
@Dao
interface ScoreDao {
    /**
     * Insert a new high score
     */
    @Insert
    suspend fun insert(highScore: HighScore): Long
    
    /**
     * Update an existing high score
     */
    @Update
    suspend fun update(highScore: HighScore)
    
    /**
     * Delete a high score
     */
    @Delete
    suspend fun delete(highScore: HighScore)
    
    /**
     * Get a high score by ID
     */
    @Query("SELECT * FROM high_scores WHERE id = :id")
    suspend fun getHighScoreById(id: Int): HighScore?
    
    /**
     * Get all high scores for a player
     */
    @Query("SELECT * FROM high_scores WHERE playerId = :playerId ORDER BY score DESC")
    fun getHighScoresForPlayer(playerId: Int): Flow<List<HighScore>>
    
    /**
     * Get top N high scores for a game mode
     */
    @Query("SELECT * FROM high_scores WHERE gameMode = :gameMode ORDER BY score DESC LIMIT :limit")
    suspend fun getTopScoresForGameMode(gameMode: String, limit: Int): List<HighScore>
    
    /**
     * Get top N high scores for all game modes
     */
    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT :limit")
    suspend fun getTopScores(limit: Int): List<HighScore>
    
    /**
     * Check if a score qualifies as a high score for a player
     */
    @Query("SELECT COUNT(*) < :maximumScores OR MIN(score) < :score FROM high_scores WHERE playerId = :playerId")
    suspend fun isHighScore(playerId: Int, score: Long, maximumScores: Int = 10): Boolean
    
    /**
     * Get detailed high scores with player names
     */
    @Query("SELECT hs.id, hs.score, hs.level_reached as levelReached, hs.lines_cleared as linesCleared, " +
           "hs.achieved_at as achievedAt, hs.gameMode, p.name as playerName " +
           "FROM high_scores hs INNER JOIN players p ON hs.playerId = p.id " +
           "ORDER BY hs.score DESC LIMIT :limit")
    suspend fun getDetailedHighScores(limit: Int): List<DetailedHighScore>
    
    /**
     * Get player's best score for a game mode
     */
    @Query("SELECT * FROM high_scores WHERE playerId = :playerId AND gameMode = :gameMode " +
           "ORDER BY score DESC LIMIT 1")
    suspend fun getPlayerBestScoreForMode(playerId: Int, gameMode: String): HighScore?
    
    /**
     * Get player's best score for all game modes
     */
    @Query("SELECT * FROM high_scores WHERE playerId = :playerId ORDER BY score DESC LIMIT 1")
    suspend fun getPlayerBestScore(playerId: Int): HighScore?
    
    /**
     * Record a new high score
     */
    @Transaction
    suspend fun recordHighScore(
        playerId: Int,
        score: Long,
        levelReached: Int,
        linesCleared: Int,
        gameMode: GameMode,
        maximumScores: Int = 10
    ) {
        // Check if this is a high score
        if (isHighScore(playerId, score, maximumScores)) {
            // Create new high score entry
            val highScore = HighScore(
                playerId = playerId,
                score = score,
                levelReached = levelReached,
                linesCleared = linesCleared,
                achievedAt = Date(),
                gameMode = gameMode.name
            )
            
            // Insert the new high score
            insert(highScore)
            
            // Delete excess high scores if more than maximum allowed
            deleteExcessHighScores(playerId, maximumScores)
        }
    }
    
    /**
     * Delete excess high scores to keep only the top N
     */
    @Query("DELETE FROM high_scores WHERE id IN " +
           "(SELECT id FROM high_scores WHERE playerId = :playerId " +
           "ORDER BY score DESC LIMIT -1 OFFSET :maxScores)")
    suspend fun deleteExcessHighScores(playerId: Int, maxScores: Int)
    
    /**
     * Delete old high scores (older than 1 year)
     */
    @Query("DELETE FROM high_scores WHERE achieved_at < datetime('now', '-1 year')")
    suspend fun deleteOldHighScores()
    
    /**
     * Data class for detailed high score information
     */
    data class DetailedHighScore(
        val id: Int,
        val score: Long,
        val levelReached: Int,
        val linesCleared: Int,
        val achievedAt: Date,
        val gameMode: String,
        val playerName: String
    )
}
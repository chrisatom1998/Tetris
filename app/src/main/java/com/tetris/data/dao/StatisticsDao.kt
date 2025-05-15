package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Statistics
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the Statistics entity.
 * Contains methods for accessing and manipulating game statistics data in the database.
 */
@Dao
interface StatisticsDao {
    /**
     * Insert new statistics entry
     */
    @Insert
    suspend fun insert(statistics: Statistics): Long
    
    /**
     * Update existing statistics
     */
    @Update
    suspend fun update(statistics: Statistics)
    
    /**
     * Delete statistics
     */
    @Delete
    suspend fun delete(statistics: Statistics)
    
    /**
     * Get statistics for a specific player
     */
    @Query("SELECT * FROM statistics WHERE playerId = :playerId")
    suspend fun getStatisticsForPlayer(playerId: Int): Statistics?
    
    /**
     * Get all statistics ordered by highest score
     */
    @Query("SELECT * FROM statistics ORDER BY highestScore DESC")
    fun getAllStatistics(): Flow<List<Statistics>>
    
    /**
     * Get top N players by highest score
     */
    @Query("SELECT * FROM statistics ORDER BY highestScore DESC LIMIT :limit")
    suspend fun getTopStatistics(limit: Int): List<Statistics>
    
    /**
     * Get average statistics across all players
     */
    @Query("SELECT AVG(totalGames) as avgGames, AVG(highestScore) as avgHighScore, " +
           "AVG(totalLinesCleared) as avgLines, AVG(totalTimePlayed) as avgTimePlayed " +
           "FROM statistics")
    suspend fun getAverageStats(): AverageStats
    
    /**
     * Add a game to statistics
     */
    @Query("UPDATE statistics SET totalGames = totalGames + 1 WHERE playerId = :playerId")
    suspend fun incrementGamesPlayed(playerId: Int)
    
    /**
     * Add a win to statistics
     */
    @Query("UPDATE statistics SET gamesWon = gamesWon + 1 WHERE playerId = :playerId")
    suspend fun incrementGamesWon(playerId: Int)
    
    /**
     * Update score in statistics if higher
     */
    @Query("UPDATE statistics SET highestScore = :score WHERE playerId = :playerId AND highestScore < :score")
    suspend fun updateHighScoreIfHigher(playerId: Int, score: Long)
    
    /**
     * Update highest level if higher
     */
    @Query("UPDATE statistics SET highestLevel = :level WHERE playerId = :playerId AND highestLevel < :level")
    suspend fun updateHighestLevelIfHigher(playerId: Int, level: Int)
    
    /**
     * Get leaderboard data
     */
    @Query("SELECT s.id, p.name as playerName, s.highestScore, s.highestLevel, " +
           "s.highestLinesInGame, s.tetrisCount " +
           "FROM statistics s INNER JOIN players p ON s.playerId = p.id " +
           "ORDER BY s.highestScore DESC LIMIT :limit")
    suspend fun getLeaderboard(limit: Int): List<LeaderboardEntry>
    
    /**
     * Get statistics for specific achievements
     */
    @Query("SELECT * FROM statistics WHERE tetrisCount >= :tetrisThreshold OR " +
           "perfectClearCount >= :perfectClearThreshold OR comboMax >= :comboThreshold")
    suspend fun getStatisticsForAchievements(
        tetrisThreshold: Int, 
        perfectClearThreshold: Int,
        comboThreshold: Int
    ): List<Statistics>
    
    /**
     * Get piece placement statistics for a player
     */
    @Query("SELECT iPiecesPlaced, jPiecesPlaced, lPiecesPlaced, " +
           "oPiecesPlaced, sPiecesPlaced, tPiecesPlaced, zPiecesPlaced " +
           "FROM statistics WHERE playerId = :playerId")
    suspend fun getPiecePlacementStats(playerId: Int): PiecePlacementStats?
    
    /**
     * Get movement statistics for a player
     */
    @Query("SELECT rotations, movesLeft, movesRight, softDropCount, hardDropCount, holdPieceCount " +
           "FROM statistics WHERE playerId = :playerId")
    suspend fun getMovementStats(playerId: Int): MovementStats?
    
    /**
     * Create statistics entry for a player if it doesn't exist
     */
    @Transaction
    suspend fun ensureStatisticsExistForPlayer(playerId: Int) {
        val stats = getStatisticsForPlayer(playerId)
        if (stats == null) {
            val newStats = Statistics.createForPlayer(playerId)
            insert(newStats)
        }
    }
    
    /**
     * Data class for average statistics query
     */
    data class AverageStats(
        val avgGames: Float,
        val avgHighScore: Float,
        val avgLines: Float,
        val avgTimePlayed: Float
    )
    
    /**
     * Data class for leaderboard entry
     */
    data class LeaderboardEntry(
        val id: Int,
        val playerName: String,
        val highestScore: Long,
        val highestLevel: Int,
        val highestLinesInGame: Int,
        val tetrisCount: Int
    )
    
    /**
     * Data class for piece placement statistics
     */
    data class PiecePlacementStats(
        val iPiecesPlaced: Int,
        val jPiecesPlaced: Int,
        val lPiecesPlaced: Int,
        val oPiecesPlaced: Int,
        val sPiecesPlaced: Int,
        val tPiecesPlaced: Int,
        val zPiecesPlaced: Int
    )
    
    /**
     * Data class for movement statistics
     */
    data class MovementStats(
        val rotations: Int,
        val movesLeft: Int,
        val movesRight: Int,
        val softDropCount: Int,
        val hardDropCount: Int,
        val holdPieceCount: Int
    )
}
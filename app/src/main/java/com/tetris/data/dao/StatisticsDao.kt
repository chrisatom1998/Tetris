package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Statistics
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the Statistics entity.
 */
@Dao
interface StatisticsDao {
    @Insert
    suspend fun insert(statistics: Statistics): Long

    @Update
    suspend fun update(statistics: Statistics) // Reverted to Unit

    @Delete
    suspend fun delete(statistics: Statistics) // Reverted to Unit

    @Query("SELECT * FROM statistics WHERE playerId = :playerId")
    suspend fun getStatisticsForPlayer(playerId: Int): Statistics?

    @Query("SELECT * FROM statistics ORDER BY highestScore DESC")
    fun getAllStatistics(): Flow<List<Statistics>>

    @Query("SELECT * FROM statistics ORDER BY highestScore DESC LIMIT :limit")
    suspend fun getTopStatistics(limit: Int): List<Statistics>

    @Query("SELECT AVG(totalGames) as avgGames, AVG(highestScore) as avgHighScore, " +
            "AVG(totalLinesCleared) as avgLines, AVG(totalTimePlayed) as avgTimePlayed " +
            "FROM statistics")
    suspend fun getAverageStats(): AverageStats

    @Query("UPDATE statistics SET totalGames = totalGames + 1 WHERE playerId = :playerId")
    suspend fun incrementGamesPlayed(playerId: Int) // Reverted to Unit

    @Query("UPDATE statistics SET gamesWon = gamesWon + 1 WHERE playerId = :playerId")
    suspend fun incrementGamesWon(playerId: Int) // Reverted to Unit

    @Query("UPDATE statistics SET highestScore = :score WHERE playerId = :playerId AND highestScore < :score")
    suspend fun updateHighScoreIfHigher(playerId: Int, score: Long) // Reverted to Unit

    @Query("UPDATE statistics SET highestLevel = :level WHERE playerId = :playerId AND highestLevel < :level")
    suspend fun updateHighestLevelIfHigher(playerId: Int, level: Int) // Reverted to Unit

    @Query("SELECT s.id, p.name as playerName, s.highestScore, s.highestLevel, " +
            "s.highestLinesInGame, s.tetrisCount " +
            "FROM statistics s INNER JOIN players p ON s.playerId = p.id " +
            "ORDER BY s.highestScore DESC LIMIT :limit")
    suspend fun getLeaderboard(limit: Int): List<LeaderboardEntry>

    @Query("SELECT * FROM statistics WHERE tetrisCount >= :tetrisThreshold OR " +
            "perfectClearCount >= :perfectClearThreshold OR comboMax >= :comboThreshold")
    suspend fun getStatisticsForAchievements(
        tetrisThreshold: Int,
        perfectClearThreshold: Int,
        comboThreshold: Int
    ): List<Statistics>

    @Query("SELECT iPiecesPlaced, jPiecesPlaced, lPiecesPlaced, " +
            "oPiecesPlaced, sPiecesPlaced, tPiecesPlaced, zPiecesPlaced " +
            "FROM statistics WHERE playerId = :playerId")
    suspend fun getPiecePlacementStats(playerId: Int): PiecePlacementStats?

    @Query("SELECT rotations, movesLeft, movesRight, softDropCount, hardDropCount, holdPieceCount " +
            "FROM statistics WHERE playerId = :playerId")
    suspend fun getMovementStats(playerId: Int): MovementStats?

    @Transaction
    suspend fun ensureStatisticsExistForPlayer(playerId: Int) {
        val stats = getStatisticsForPlayer(playerId)
        if (stats == null) {
            val newStats = Statistics.createForPlayer(playerId) // Assuming a static method
            insert(newStats)
        }
    }

    data class AverageStats(
        val avgGames: Float,
        val avgHighScore: Float,
        val avgLines: Float,
        val avgTimePlayed: Float
    )

    data class LeaderboardEntry(
        val id: Int,
        val playerName: String,
        val highestScore: Long,
        val highestLevel: Int,
        val highestLinesInGame: Int,
        val tetrisCount: Int
    )

    data class PiecePlacementStats(
        val iPiecesPlaced: Int,
        val jPiecesPlaced: Int,
        val lPiecesPlaced: Int,
        val oPiecesPlaced: Int,
        val sPiecesPlaced: Int,
        val tPiecesPlaced: Int,
        val zPiecesPlaced: Int
    )

    data class MovementStats(
        val rotations: Int,
        val movesLeft: Int,
        val movesRight: Int,
        val softDropCount: Int,
        val hardDropCount: Int,
        val holdPieceCount: Int
    )
}
package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.HighScore
import com.tetris.model.GameMode // Assuming GameMode is an enum or similar
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * DAO (Data Access Object) for the HighScore entity.
 */
@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(highScore: HighScore): Long

    @Update
    suspend fun update(highScore: HighScore) // Reverted to Unit

    @Delete
    suspend fun delete(highScore: HighScore) // Reverted to Unit

    @Query("SELECT * FROM high_scores WHERE id = :id")
    suspend fun getHighScoreById(id: Int): HighScore?

    @Query("SELECT * FROM high_scores WHERE playerId = :playerId ORDER BY score DESC")
    fun getHighScoresForPlayer(playerId: Int): Flow<List<HighScore>>

    @Query("SELECT * FROM high_scores WHERE gameMode = :gameMode ORDER BY score DESC LIMIT :limit")
    suspend fun getTopScoresForGameMode(gameMode: String, limit: Int): List<HighScore>

    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT :limit")
    suspend fun getTopScores(limit: Int): List<HighScore>

    @Query("SELECT COUNT(*) < :maximumScores OR MIN(score) < :score FROM high_scores WHERE playerId = :playerId")
    suspend fun isHighScore(playerId: Int, score: Long, maximumScores: Int = 10): Boolean

    @Query("SELECT hs.id, hs.score, hs.levelReached as levelReached, hs.linesCleared as linesCleared, " +
            "hs.achievedAt as achievedAt, hs.gameMode, p.name as playerName " +
            "FROM high_scores hs INNER JOIN players p ON hs.playerId = p.id " +
            "ORDER BY hs.score DESC LIMIT :limit")
    suspend fun getDetailedHighScores(limit: Int): List<DetailedHighScore>

    @Query("SELECT * FROM high_scores WHERE playerId = :playerId AND gameMode = :gameMode " +
            "ORDER BY score DESC LIMIT 1")
    suspend fun getPlayerBestScoreForMode(playerId: Int, gameMode: String): HighScore?

    @Query("SELECT * FROM high_scores WHERE playerId = :playerId ORDER BY score DESC LIMIT 1")
    suspend fun getPlayerBestScore(playerId: Int): HighScore?

    @Transaction
    suspend fun recordHighScore(
        playerId: Int,
        score: Long,
        levelReached: Int,
        linesCleared: Int,
        gameMode: GameMode,
        maximumScores: Int = 10
    ) {
        if (isHighScore(playerId, score, maximumScores)) {
            val highScoreEntry = HighScore( // Renamed to avoid conflict with the DAO method
                playerId = playerId,
                score = score,
                levelReached = levelReached,
                linesCleared = linesCleared,
                achievedAt = Date(),
                gameMode = gameMode.name
            )
            insert(highScoreEntry)
            deleteExcessHighScores(playerId, maximumScores)
        }
    }

    @Query("DELETE FROM high_scores WHERE id IN " +
            "(SELECT id FROM high_scores WHERE playerId = :playerId " +
            "ORDER BY score DESC LIMIT -1 OFFSET :maxScores)")
    suspend fun deleteExcessHighScores(playerId: Int, maxScores: Int) // Reverted to Unit

    @Query("DELETE FROM high_scores WHERE achievedAt < datetime('now', '-1 year')")
    suspend fun deleteOldHighScores() // Reverted to Unit

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
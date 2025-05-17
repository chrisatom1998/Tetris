package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Player
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the Player entity.
 */
@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: Player): Long

    @Update
    suspend fun update(player: Player) // Reverted to Unit

    @Delete
    suspend fun delete(player: Player) // Reverted to Unit

    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayerById(playerId: Int): Player?

    @Query("SELECT * FROM players WHERE name = :name LIMIT 1")
    suspend fun getPlayerByName(name: String): Player?

    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM players ORDER BY highScore DESC")
    fun getAllPlayersByScore(): Flow<List<Player>>

    @Query("SELECT * FROM players ORDER BY id DESC LIMIT 1")
    suspend fun getLastActivePlayer(): Player?

    @Query("SELECT * FROM players ORDER BY highScore DESC LIMIT :limit")
    suspend fun getTopPlayers(limit: Int): List<Player>

    @Query("UPDATE players SET highScore = :score WHERE id = :playerId AND highScore < :score")
    suspend fun updateHighScoreIfHigher(playerId: Int, score: Long) // Reverted to Unit

    @Query("UPDATE players SET totalGames = totalGames + 1 WHERE id = :playerId")
    suspend fun incrementGamesPlayed(playerId: Int) // Reverted to Unit

    @Query("UPDATE players SET totalScore = totalScore + :score, totalLinesCleared = totalLinesCleared + :lines, " +
            "totalTimePlayed = totalTimePlayed + :time WHERE id = :playerId")
    suspend fun updatePlayerStats(playerId: Int, score: Long, lines: Int, time: Long) // Reverted to Unit

    @Query("SELECT COUNT(*) FROM players")
    suspend fun getPlayerCount(): Int
}
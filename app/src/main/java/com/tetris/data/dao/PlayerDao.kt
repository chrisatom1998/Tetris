package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.Player
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the Player entity.
 * Contains methods for accessing and manipulating player data in the database.
 */
@Dao
interface PlayerDao {
    /**
     * Insert a new player and return the generated ID
     */
    @Insert
    suspend fun insert(player: Player): Long
    
    /**
     * Update an existing player
     */
    @Update
    suspend fun update(player: Player)
    
    /**
     * Delete a player
     */
    @Delete
    suspend fun delete(player: Player)
    
    /**
     * Get a player by their ID
     */
    @Query("SELECT * FROM players WHERE id = :playerId")
    suspend fun getPlayerById(playerId: Int): Player?
    
    /**
     * Get a player by their name
     */
    @Query("SELECT * FROM players WHERE name = :name LIMIT 1")
    suspend fun getPlayerByName(name: String): Player?
    
    /**
     * Get all players
     */
    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<Player>>
    
    /**
     * Get all players ordered by high score
     */
    @Query("SELECT * FROM players ORDER BY highScore DESC")
    fun getAllPlayersByScore(): Flow<List<Player>>
    
    /**
     * Get the most recent player (for auto-login)
     */
    @Query("SELECT * FROM players ORDER BY id DESC LIMIT 1")
    suspend fun getLastActivePlayer(): Player?
    
    /**
     * Get the top N players by high score
     */
    @Query("SELECT * FROM players ORDER BY highScore DESC LIMIT :limit")
    suspend fun getTopPlayers(limit: Int): List<Player>
    
    /**
     * Update player's high score if the new score is higher
     */
    @Query("UPDATE players SET highScore = :score WHERE id = :playerId AND highScore < :score")
    suspend fun updateHighScoreIfHigher(playerId: Int, score: Long)
    
    /**
     * Increment the player's total games count
     */
    @Query("UPDATE players SET totalGames = totalGames + 1 WHERE id = :playerId")
    suspend fun incrementGamesPlayed(playerId: Int)
    
    /**
     * Update player statistics after a game
     */
    @Query("UPDATE players SET totalScore = totalScore + :score, totalLinesCleared = totalLinesCleared + :lines, " +
           "totalTimePlayed = totalTimePlayed + :time WHERE id = :playerId")
    suspend fun updatePlayerStats(playerId: Int, score: Long, lines: Int, time: Long)
    
    /**
     * Count the total number of players
     */
    @Query("SELECT COUNT(*) FROM players")
    suspend fun getPlayerCount(): Int
}
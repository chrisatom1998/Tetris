package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.GameState
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the GameState entity.
 * Contains methods for accessing and manipulating game state data in the database.
 */
@Dao
interface GameStateDao {
    /**
     * Insert a new game state 
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameState: GameState): Long
    
    /**
     * Update an existing game state
     */
    @Update
    suspend fun update(gameState: GameState)
    
    /**
     * Delete a game state
     */
    @Delete
    suspend fun delete(gameState: GameState)
    
    /**
     * Insert a new game state or update if it already exists
     */
    @Transaction
    suspend fun insertOrUpdate(gameState: GameState) {
        val id = if (gameState.id > 0) {
            update(gameState)
            gameState.id.toLong()
        } else {
            insert(gameState)
        }
    }
    
    /**
     * Get a game state by ID
     */
    @Query("SELECT * FROM game_states WHERE id = :id")
    suspend fun getGameStateById(id: Int): GameState?
    
    /**
     * Get the most recent active game state for a player
     */
    @Query("SELECT * FROM game_states WHERE playerId = :playerId AND isGameOver = 0 ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getActiveGameStateForPlayer(playerId: Int): GameState?
    
    /**
     * Get all game states for a player
     */
    @Query("SELECT * FROM game_states WHERE playerId = :playerId ORDER BY lastUpdated DESC")
    fun getAllGameStatesForPlayer(playerId: Int): Flow<List<GameState>>
    
    /**
     * Delete all game states for a player
     */
    @Query("DELETE FROM game_states WHERE playerId = :playerId")
    suspend fun deleteByPlayerId(playerId: Int)
    
    /**
     * Delete all completed (game over) game states for a player
     */
    @Query("DELETE FROM game_states WHERE playerId = :playerId AND isGameOver = 1")
    suspend fun deleteCompletedGamesForPlayer(playerId: Int)
    
    /**
     * Count the active games for a player
     */
    @Query("SELECT COUNT(*) FROM game_states WHERE playerId = :playerId AND isGameOver = 0")
    suspend fun countActiveGamesForPlayer(playerId: Int): Int
    
    /**
     * Mark all games as over for a player
     */
    @Query("UPDATE game_states SET isGameOver = 1 WHERE playerId = :playerId")
    suspend fun markAllGamesAsOverForPlayer(playerId: Int)
    
    /**
     * Update the time elapsed for a game
     */
    @Query("UPDATE game_states SET timeElapsed = :timeElapsed WHERE id = :gameStateId")
    suspend fun updateTimeElapsed(gameStateId: Int, timeElapsed: Long)
    
    /**
     * Delete old saved games (older than 7 days)
     */
    @Query("DELETE FROM game_states WHERE lastUpdated < datetime('now', '-7 days')")
    suspend fun cleanupOldGames()
}
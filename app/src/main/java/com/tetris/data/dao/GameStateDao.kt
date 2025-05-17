package com.tetris.data.dao

import androidx.room.*
import com.tetris.model.GameState
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for the GameState entity.
 */
@Dao
interface GameStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameState: GameState): Long

    @Update
    suspend fun update(gameState: GameState) // Reverted to Unit

    @Delete
    suspend fun delete(gameState: GameState) // Reverted to Unit

    @Transaction
    suspend fun insertOrUpdate(gameState: GameState) {
        if (gameState.id > 0) {
            update(gameState)
        } else {
            insert(gameState)
        }
    }

    @Query("SELECT * FROM game_states WHERE id = :id")
    suspend fun getGameStateById(id: Int): GameState?

    @Query("SELECT * FROM game_states WHERE playerId = :playerId AND isGameOver = 0 ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getActiveGameStateForPlayer(playerId: Int): GameState?

    @Query("SELECT * FROM game_states WHERE playerId = :playerId ORDER BY lastUpdated DESC")
    fun getAllGameStatesForPlayer(playerId: Int): Flow<List<GameState>>

    @Query("DELETE FROM game_states WHERE playerId = :playerId")
    suspend fun deleteByPlayerId(playerId: Int) // Reverted to Unit

    @Query("DELETE FROM game_states WHERE playerId = :playerId AND isGameOver = 1")
    suspend fun deleteCompletedGamesForPlayer(playerId: Int) // Reverted to Unit

    @Query("SELECT COUNT(*) FROM game_states WHERE playerId = :playerId AND isGameOver = 0")
    suspend fun countActiveGamesForPlayer(playerId: Int): Int

    @Query("UPDATE game_states SET isGameOver = 1 WHERE playerId = :playerId")
    suspend fun markAllGamesAsOverForPlayer(playerId: Int) // Reverted to Unit

    @Query("UPDATE game_states SET timeElapsed = :timeElapsed WHERE id = :gameStateId")
    suspend fun updateTimeElapsed(gameStateId: Int, timeElapsed: Long) // Reverted to Unit

    @Query("DELETE FROM game_states WHERE lastUpdated < datetime('now', '-7 days')")
    suspend fun cleanupOldGames() // Reverted to Unit
}
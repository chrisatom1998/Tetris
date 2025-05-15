package com.tetris.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a high score entry in the game.
 * Stores the score value along with other game statistics for leaderboards.
 */
@Entity(
    tableName = "high_scores",
    foreignKeys = [
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HighScore(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // Foreign key to Player
    val playerId: Int,
    
    // Score value
    val score: Long,
    
    // Game details
    val levelReached: Int,
    val linesCleared: Int,
    
    // When the score was achieved
    val achievedAt: Date = Date(),
    
    // Game mode for this score
    val gameMode: String,
    
    // Additional stats (optional)
    val tetrisCount: Int = 0,
    val maxCombo: Int = 0,
    val perfectClearCount: Int = 0,
    val gameDuration: Long = 0, // In milliseconds
    
    // Platform information
    val deviceModel: String = android.os.Build.MODEL,
    val appVersion: String = "1.0.0"
) {
    companion object {
        /**
         * Create a high score entry from game state
         */
        fun fromGameState(gameState: GameState, playerId: Int): HighScore {
            return HighScore(
                playerId = playerId,
                score = gameState.score,
                levelReached = gameState.level,
                linesCleared = gameState.linesCleared,
                gameMode = gameState.gameMode.name,
                tetrisCount = gameState.tetrisCount,
                maxCombo = gameState.maxCombo,
                perfectClearCount = gameState.perfectClearCount,
                gameDuration = gameState.timeElapsed
            )
        }
    }
}
package com.tetris.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a player entity for the game.
 * This class is used to store player information in the database.
 */
@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // Player's display name
    var name: String,
    
    // Date the player profile was created
    val createdAt: Date = Date(),
    
    // Statistics tracking
    var totalGames: Int = 0,
    var totalScore: Long = 0,
    var highScore: Long = 0,
    var totalLinesCleared: Int = 0,
    var maxLevel: Int = 0,
    var totalTimePlayed: Long = 0, // In milliseconds
    
    // Special achievement tracking
    var tetrisCount: Int = 0, // Number of four-line clears
    var tspin: Int = 0,       // Number of T-spins
    var perfectClearCount: Int = 0 // Number of perfect clears
) {
    /**
     * Update the player's statistics after a game
     * 
     * @param score Score achieved in the game
     * @param linesCleared Number of lines cleared
     * @param level Maximum level reached
     * @param timePlayed Time played in milliseconds
     * @param tetrisCount Number of tetris clears (4 lines)
     * @param perfectClear Whether a perfect clear was achieved
     */
    fun updateStats(
        score: Long,
        linesCleared: Int,
        level: Int,
        timePlayed: Long,
        tetrisCount: Int = 0,
        tspinCount: Int = 0,
        perfectClear: Boolean = false
    ) {
        totalGames++
        totalScore += score
        totalLinesCleared += linesCleared
        totalTimePlayed += timePlayed
        this.tetrisCount += tetrisCount
        this.tspin += tspinCount
        
        // Update high score if current score is higher
        if (score > highScore) {
            highScore = score
        }
        
        // Update max level if current level is higher
        if (level > maxLevel) {
            maxLevel = level
        }
        
        // Increment perfect clear counter if applicable
        if (perfectClear) {
            perfectClearCount++
        }
    }
    
    /**
     * Calculate average score per game
     */
    fun getAverageScore(): Long {
        return if (totalGames > 0) totalScore / totalGames else 0
    }
    
    /**
     * Calculate average lines cleared per game
     */
    fun getAverageLinesPerGame(): Double {
        return if (totalGames > 0) totalLinesCleared.toDouble() / totalGames else 0.0
    }
    
    /**
     * Calculate average time played per game (in seconds)
     */
    fun getAverageTimePerGame(): Double {
        return if (totalGames > 0) totalTimePlayed.toDouble() / (totalGames * 1000) else 0.0
    }
    
    /**
     * Get total play time formatted as hours:minutes:seconds
     */
    fun getFormattedTotalPlayTime(): String {
        val totalSeconds = totalTimePlayed / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        
        return String.format("%d:%02d:%02d", hours, minutes, seconds)
    }
    
    companion object {
        /**
         * Create a default player with the given name
         */
        fun createDefault(name: String): Player {
            return Player(name = name)
        }
    }
}
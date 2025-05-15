package com.tetris.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents detailed statistics for a player.
 * This data is stored in the database and updated after each game.
 */
@Entity(
    tableName = "statistics",
    foreignKeys = [ForeignKey(
        entity = Player::class,
        parentColumns = ["id"],
        childColumns = ["playerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Statistics(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // Reference to the player
    val playerId: Int,
    
    // Basic statistics
    var totalGames: Int = 0,
    var gamesWon: Int = 0, // For challenge modes with win conditions
    var totalScore: Long = 0,
    var highestScore: Long = 0,
    var totalLinesCleared: Int = 0,
    var highestLinesInGame: Int = 0,
    var totalPiecesPlaced: Int = 0,
    var highestLevel: Int = 0,
    var totalTimePlayed: Long = 0, // In milliseconds
    var longestGame: Long = 0, // In milliseconds
    var fastestClearTime: Long? = null, // For challenge modes, in milliseconds
    
    // Line clear statistics
    var singleLines: Int = 0, // Number of single line clears
    var doubleLines: Int = 0, // Number of double line clears
    var tripleLines: Int = 0, // Number of triple line clears
    var tetrisCount: Int = 0, // Number of tetris clears (four lines)
    
    // Special move statistics
    var tSpins: Int = 0,
    var tSpinSingles: Int = 0,
    var tSpinDoubles: Int = 0,
    var tSpinTriples: Int = 0,
    var perfectClearCount: Int = 0,
    var comboMax: Int = 0,
    var backToBackCount: Int = 0,
    
    // Piece-specific statistics
    var iPiecesPlaced: Int = 0,
    var jPiecesPlaced: Int = 0,
    var lPiecesPlaced: Int = 0,
    var oPiecesPlaced: Int = 0,
    var sPiecesPlaced: Int = 0,
    var tPiecesPlaced: Int = 0,
    var zPiecesPlaced: Int = 0,
    
    // Session statistics
    var lastPlayed: Date = Date(),
    var currentStreak: Int = 0, // Days played in a row
    var longestStreak: Int = 0,
    var achievement: Int = 0, // Number of achievements unlocked
    
    // Movement statistics
    var rotations: Int = 0,
    var movesLeft: Int = 0,
    var movesRight: Int = 0,
    var softDropCount: Int = 0,
    var hardDropCount: Int = 0,
    var holdPieceCount: Int = 0
) {
    /**
     * Update statistics after a game
     */
    fun updateAfterGame(
        score: Long,
        linesCleared: Int,
        level: Int,
        timePlayed: Long,
        piecesPlaced: Int,
        singles: Int,
        doubles: Int,
        triples: Int,
        tetris: Int,
        tSpins: Int,
        tSpinSingles: Int,
        tSpinDoubles: Int,
        tSpinTriples: Int,
        perfectClears: Int,
        maxCombo: Int,
        backToBack: Int,
        iPieces: Int,
        jPieces: Int,
        lPieces: Int,
        oPieces: Int,
        sPieces: Int,
        tPieces: Int,
        zPieces: Int,
        rotations: Int,
        leftMoves: Int,
        rightMoves: Int,
        softDrops: Int,
        hardDrops: Int,
        holdCount: Int,
        won: Boolean = false
    ) {
        // Update basic statistics
        totalGames++
        if (won) gamesWon++
        totalScore += score
        if (score > highestScore) highestScore = score
        totalLinesCleared += linesCleared
        if (linesCleared > highestLinesInGame) highestLinesInGame = linesCleared
        totalPiecesPlaced += piecesPlaced
        if (level > highestLevel) highestLevel = level
        totalTimePlayed += timePlayed
        if (timePlayed > longestGame) longestGame = timePlayed
        
        // Update line clear statistics
        singleLines += singles
        doubleLines += doubles
        tripleLines += triples
        tetrisCount += tetris
        
        // Update special move statistics
        this.tSpins += tSpins
        this.tSpinSingles += tSpinSingles
        this.tSpinDoubles += tSpinDoubles
        this.tSpinTriples += tSpinTriples
        perfectClearCount += perfectClears
        if (maxCombo > comboMax) comboMax = maxCombo
        backToBackCount += backToBack
        
        // Update piece statistics
        iPiecesPlaced += iPieces
        jPiecesPlaced += jPieces
        lPiecesPlaced += lPieces
        oPiecesPlaced += oPieces
        sPiecesPlaced += sPieces
        tPiecesPlaced += tPieces
        zPiecesPlaced += zPieces
        
        // Update movement statistics
        this.rotations += rotations
        movesLeft += leftMoves
        movesRight += rightMoves
        softDropCount += softDrops
        hardDropCount += hardDrops
        holdPieceCount += holdCount
        
        // Update last played date
        lastPlayed = Date()
        
        // Streak management would typically be handled elsewhere since it depends on calendar days
    }
    
    /**
     * Calculate average score per game
     */
    fun averageScorePerGame(): Long {
        return if (totalGames > 0) totalScore / totalGames else 0
    }
    
    /**
     * Calculate average lines per game
     */
    fun averageLinesPerGame(): Double {
        return if (totalGames > 0) totalLinesCleared.toDouble() / totalGames else 0.0
    }
    
    /**
     * Calculate average time per game in seconds
     */
    fun averageTimePerGame(): Double {
        return if (totalGames > 0) totalTimePlayed.toDouble() / (totalGames * 1000) else 0.0
    }
    
    /**
     * Calculate win rate percentage
     */
    fun winRate(): Double {
        return if (totalGames > 0) gamesWon.toDouble() * 100 / totalGames else 0.0
    }
    
    companion object {
        /**
         * Create new statistics for a player
         */
        fun createForPlayer(playerId: Int): Statistics {
            return Statistics(playerId = playerId)
        }
    }
}
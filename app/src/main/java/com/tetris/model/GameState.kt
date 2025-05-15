package com.tetris.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tetris.data.converters.DateConverter
import com.tetris.data.converters.PieceConverter
import com.tetris.data.converters.BoardConverter
import java.util.Date

/**
 * Represents the complete state of a Tetris game.
 * This class is used to save and restore games.
 */
@Entity(
    tableName = "game_states",
    foreignKeys = [ForeignKey(
        entity = Player::class,
        parentColumns = ["id"],
        childColumns = ["playerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(DateConverter::class, BoardConverter::class, PieceConverter::class)
data class GameState(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // Reference to the player
    val playerId: Int,
    
    // The current game board
    var board: Board,
    
    // Current active piece
    var currentPiece: Piece?,
    
    // Next piece to appear
    var nextPiece: Piece?,
    
    // Held piece (can be null if no piece is held)
    var heldPiece: Piece? = null,
    
    // Whether the player has already held a piece in this turn
    var hasHeld: Boolean = false,
    
    // Game score
    var score: Long = 0,
    
    // Current level
    var level: Int = 1,
    
    // Lines cleared so far
    var linesCleared: Int = 0,
    
    // Current combo counter
    var combo: Int = 0,
    
    // Back-to-back counter (counts consecutive difficult line clears)
    var backToBack: Int = 0,
    
    // Game statistics for this session
    var singleLines: Int = 0,
    var doubleLines: Int = 0,
    var tripleLines: Int = 0,
    var tetrisCount: Int = 0,
    var tSpins: Int = 0,
    var tSpinSingles: Int = 0,
    var tSpinDoubles: Int = 0,
    var tSpinTriples: Int = 0,
    var perfectClearCount: Int = 0,
    var maxCombo: Int = 0,
    
    // Piece statistics
    var iPiecesPlaced: Int = 0,
    var jPiecesPlaced: Int = 0,
    var lPiecesPlaced: Int = 0,
    var oPiecesPlaced: Int = 0,
    var sPiecesPlaced: Int = 0,
    var tPiecesPlaced: Int = 0,
    var zPiecesPlaced: Int = 0,
    
    // Movement statistics
    var rotations: Int = 0,
    var movesLeft: Int = 0,
    var movesRight: Int = 0,
    var softDropCount: Int = 0,
    var hardDropCount: Int = 0,
    var holdPieceCount: Int = 0,
    
    // Total pieces placed in this game
    var totalPiecesPlaced: Int = 0,
    
    // Time tracking
    var timeElapsed: Long = 0, // In milliseconds
    
    // Flag indicating if the game is active or over
    var isGameOver: Boolean = false,
    
    // Flag indicating if the game is paused
    var isPaused: Boolean = false,
    
    // Timestamp when the state was saved
    var lastUpdated: Date = Date(),
    
    // Game mode
    var gameMode: GameMode = GameMode.MARATHON,
    
    // Random seed for piece generation (for consistent piece sequence when resuming)
    var randomSeed: Long = System.currentTimeMillis()
) {
    /**
     * Update the statistics after clearing lines
     * 
     * @param linesCleared Number of lines cleared
     * @param isTSpin Whether the move was a T-spin
     * @param isPerfectClear Whether the board is now empty
     */
    fun updateAfterLineClear(linesCleared: Int, isTSpin: Boolean = false, isPerfectClear: Boolean = false) {
        // Update lines cleared
        this.linesCleared += linesCleared
        
        // Update combo
        if (linesCleared > 0) {
            combo++
            if (combo > maxCombo) maxCombo = combo
        } else {
            combo = 0
        }
        
        // Update specific line clear counts
        when (linesCleared) {
            1 -> {
                if (isTSpin) {
                    tSpins++
                    tSpinSingles++
                    // Update back-to-back for difficult clears
                    backToBack++
                } else {
                    singleLines++
                    // Reset back-to-back for easy clears
                    backToBack = 0
                }
            }
            2 -> {
                if (isTSpin) {
                    tSpins++
                    tSpinDoubles++
                    backToBack++
                } else {
                    doubleLines++
                    backToBack = 0
                }
            }
            3 -> {
                if (isTSpin) {
                    tSpins++
                    tSpinTriples++
                    backToBack++
                } else {
                    tripleLines++
                    backToBack = 0
                }
            }
            4 -> {
                tetrisCount++
                backToBack++
            }
        }
        
        // Update perfect clear counter
        if (isPerfectClear) {
            perfectClearCount++
        }
        
        // Calculate score based on line clears, level, combo, back-to-back, etc.
        updateScore(linesCleared, isTSpin, isPerfectClear)
        
        // Check if level up is needed
        checkLevelUp()
    }
    
    /**
     * Update score based on game actions
     */
    private fun updateScore(linesCleared: Int, isTSpin: Boolean, isPerfectClear: Boolean) {
        // Base points for different line clears
        val basePoints = when (linesCleared) {
            1 -> if (isTSpin) 800 else 100
            2 -> if (isTSpin) 1200 else 300
            3 -> if (isTSpin) 1600 else 500
            4 -> 800 // Tetris
            else -> 0
        }
        
        // Apply level multiplier
        var points = basePoints * level
        
        // Apply combo bonus
        if (combo > 1) {
            points += (50 * combo * level)
        }
        
        // Apply back-to-back bonus
        if (backToBack > 1) {
            points = (points * 1.5).toInt()
        }
        
        // Apply perfect clear bonus
        if (isPerfectClear) {
            points += (3000 * level)
        }
        
        // Update total score
        score += points
    }
    
    /**
     * Check if the player should level up based on lines cleared
     */
    private fun checkLevelUp() {
        // Level up every 10 lines (standard rule)
        val newLevel = (linesCleared / 10) + 1
        if (newLevel > level) {
            level = newLevel
        }
    }
    
    /**
     * Update piece statistics after placing a piece
     */
    fun updatePiecePlacement(piece: Piece) {
        totalPiecesPlaced++
        
        // Update piece type counter
        when (piece.type) {
            PieceType.I -> iPiecesPlaced++
            PieceType.J -> jPiecesPlaced++
            PieceType.L -> lPiecesPlaced++
            PieceType.O -> oPiecesPlaced++
            PieceType.S -> sPiecesPlaced++
            PieceType.T -> tPiecesPlaced++
            PieceType.Z -> zPiecesPlaced++
        }
    }
    
    /**
     * Update movement statistics
     */
    fun updateMovementStats(moveType: MoveType) {
        when (moveType) {
            MoveType.ROTATE -> rotations++
            MoveType.LEFT -> movesLeft++
            MoveType.RIGHT -> movesRight++
            MoveType.SOFT_DROP -> softDropCount++
            MoveType.HARD_DROP -> hardDropCount++
            MoveType.HOLD -> holdPieceCount++
        }
    }
    
    /**
     * Extract game statistics to update player profile
     */
    fun extractStatistics(): Map<String, Any> {
        return mapOf(
            "score" to score,
            "level" to level,
            "linesCleared" to linesCleared,
            "timeElapsed" to timeElapsed,
            "singleLines" to singleLines,
            "doubleLines" to doubleLines,
            "tripleLines" to tripleLines,
            "tetrisCount" to tetrisCount,
            "tSpins" to tSpins,
            "tSpinSingles" to tSpinSingles,
            "tSpinDoubles" to tSpinDoubles,
            "tSpinTriples" to tSpinTriples,
            "perfectClearCount" to perfectClearCount,
            "maxCombo" to maxCombo,
            "backToBack" to backToBack,
            "iPiecesPlaced" to iPiecesPlaced,
            "jPiecesPlaced" to jPiecesPlaced,
            "lPiecesPlaced" to lPiecesPlaced,
            "oPiecesPlaced" to oPiecesPlaced,
            "sPiecesPlaced" to sPiecesPlaced,
            "tPiecesPlaced" to tPiecesPlaced,
            "zPiecesPlaced" to zPiecesPlaced,
            "rotations" to rotations,
            "movesLeft" to movesLeft,
            "movesRight" to movesRight,
            "softDropCount" to softDropCount,
            "hardDropCount" to hardDropCount,
            "holdPieceCount" to holdPieceCount,
            "totalPiecesPlaced" to totalPiecesPlaced
        )
    }
    
    companion object {
        /**
         * Create a new game state for a player
         */
        fun createNewGame(
            playerId: Int, 
            boardWidth: Int = 10, 
            boardHeight: Int = 20,
            level: Int = 1,
            gameMode: GameMode = GameMode.MARATHON
        ): GameState {
            val board = Board(boardWidth, boardHeight)
            
            // Use a fixed seed for predictable piece generation
            val seed = System.currentTimeMillis()
            
            return GameState(
                playerId = playerId,
                board = board,
                currentPiece = null,
                nextPiece = null,
                level = level,
                gameMode = gameMode,
                randomSeed = seed
            )
        }
    }
}

/**
 * Enum defining different game modes
 */
enum class GameMode {
    // Standard endless mode
    MARATHON,
    
    // Sprint mode (clear 40 lines as fast as possible)
    SPRINT,
    
    // Ultra mode (score as many points as possible in 2 minutes)
    ULTRA,
    
    // Battle mode (for multiplayer)
    BATTLE,
    
    // Practice mode
    PRACTICE
}

/**
 * Enum defining different move types for statistics tracking
 */
enum class MoveType {
    ROTATE,
    LEFT,
    RIGHT,
    SOFT_DROP,
    HARD_DROP,
    HOLD
}
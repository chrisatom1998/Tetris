package com.tetris.game

import android.graphics.Point
import android.os.Handler
import android.os.SystemClock
import com.tetris.model.Board
import com.tetris.model.GameMode
import com.tetris.model.GameState
import com.tetris.model.MoveType
import com.tetris.model.Piece
import com.tetris.model.PieceType
import java.util.Random
import kotlin.math.max
import kotlin.math.min

/**
 * The core engine class for the Tetris game.
 * Handles game loop, piece movement, collision detection, and scoring.
 */
class TetrisEngine(
    private val boardWidth: Int = 10,
    private val boardHeight: Int = 20,
    private val callback: TetrisCallback,
    private val initialLevel: Int = 1,
    private val gameMode: GameMode = GameMode.MARATHON
) {
    // Game state objects
    private var gameState: GameState
    private val board: Board
    private var currentPiece: Piece? = null
    private var nextPiece: Piece? = null
    private var heldPiece: Piece? = null
    private var hasHeld = false
    
    // Game status
    private var isRunning = false
    private var isPaused = false
    private var isGameOver = false
    
    // Timing variables
    private var lastFrameTime = 0L
    private var dropInterval = calculateDropInterval(initialLevel)
    private var lockDelay = DEFAULT_LOCK_DELAY
    private var lockDelayTimer = 0L
    private var gameStartTime = 0L
    private var totalPauseTime = 0L
    private var lastPauseTime = 0L
    private var timeElapsed = 0L
    
    // Random generator for pieces
    private val random: Random
    private val nextBag = mutableListOf<PieceType>()
    
    // Handler for the game loop
    private val handler = Handler()
    private val gameLoopRunnable = object : Runnable {
        override fun run() {
            if (isRunning && !isPaused) {
                update()
                handler.postDelayed(this, FRAME_DELAY)
            }
        }
    }
    
    init {
        // Initialize with a player ID of 0, will be updated when game is saved
        gameState = GameState.createNewGame(
            playerId = 0,
            boardWidth = boardWidth,
            boardHeight = boardHeight,
            level = initialLevel,
            gameMode = gameMode
        )
        board = gameState.board
        
        // Initialize random generator with seed
        random = Random(gameState.randomSeed)
        
        // Generate initial pieces
        spawnNextPiece()
        spawnPiece()
    }
    
    /**
     * Starts the game loop
     */
    fun start() {
        if (!isRunning) {
            isRunning = true
            isPaused = false
            gameStartTime = SystemClock.uptimeMillis()
            lastFrameTime = gameStartTime
            handler.post(gameLoopRunnable)
            callback.onGameStart()
        }
    }
    
    /**
     * Pauses the game
     */
    fun pause() {
        if (isRunning && !isPaused) {
            isPaused = true
            lastPauseTime = SystemClock.uptimeMillis()
            callback.onGamePause()
        }
    }
    
    /**
     * Resumes the game from a paused state
     */
    fun resume() {
        if (isRunning && isPaused) {
            val now = SystemClock.uptimeMillis()
            totalPauseTime += (now - lastPauseTime)
            lastPauseTime = 0
            isPaused = false
            handler.post(gameLoopRunnable)
            callback.onGameResume()
        }
    }
    
    /**
     * Stops the game
     */
    fun stop() {
        isRunning = false
        handler.removeCallbacks(gameLoopRunnable)
        updateElapsedTime()
        callback.onGameStop()
    }
    
    /**
     * The main game update loop
     */
    private fun update() {
        val now = SystemClock.uptimeMillis()
        val delta = now - lastFrameTime
        lastFrameTime = now
        
        if (isGameOver) {
            stop()
            return
        }
        
        // Update elapsed time
        updateElapsedTime()
        
        // Update game mode specific logic
        updateGameMode()
        
        // Update piece falling
        if (currentPiece != null) {
            // Check if it's time to drop the piece
            if (delta >= dropInterval) {
                if (!movePieceDown()) {
                    // Piece can't move down anymore, lock it after delay
                    if (lockDelayTimer <= 0) {
                        lockDelayTimer = now
                    } else if (now - lockDelayTimer >= lockDelay) {
                        lockPiece()
                    }
                } else {
                    // Reset lock delay if piece successfully moved down
                    lockDelayTimer = 0
                }
            }
        } else {
            // No current piece, spawn a new one
            spawnPiece()
        }
        
        // Render the game
        callback.onRender(board, currentPiece, nextPiece, heldPiece)
    }
    
    /**
     * Updates game mode specific logic
     */
    private fun updateGameMode() {
        when (gameMode) {
            GameMode.SPRINT -> {
                // Check win condition for Sprint mode (clear 40 lines)
                if (gameState.linesCleared >= 40) {
                    isRunning = false
                    callback.onGameWin()
                }
            }
            GameMode.ULTRA -> {
                // Check end condition for Ultra mode (2 minutes)
                if (timeElapsed >= 120000) { // 2 minutes in milliseconds
                    isRunning = false
                    callback.onGameWin()
                }
            }
            else -> {
                // Other modes don't have specific time-based logic
            }
        }
    }
    
    /**
     * Updates the elapsed game time
     */
    private fun updateElapsedTime() {
        if (isRunning && !isPaused) {
            val now = SystemClock.uptimeMillis()
            timeElapsed = now - gameStartTime - totalPauseTime
            gameState.timeElapsed = timeElapsed
        }
    }
    
    /**
     * Creates a new piece of a random type and adds it to the queue
     */
    private fun getNextPieceType(): PieceType {
        // Implement 7-bag randomization
        if (nextBag.isEmpty()) {
            val allPieces = PieceType.values().toMutableList()
            while (allPieces.isNotEmpty()) {
                val index = random.nextInt(allPieces.size)
                nextBag.add(allPieces[index])
                allPieces.removeAt(index)
            }
        }
        
        return nextBag.removeAt(0)
    }
    
    /**
     * Generates the next piece
     */
    private fun spawnNextPiece() {
        nextPiece = Piece(getNextPieceType())
        gameState.nextPiece = nextPiece
    }
    
    /**
     * Spawns a new piece at the top of the board
     */
    private fun spawnPiece() {
        // Move next piece to current
        currentPiece = nextPiece
        gameState.currentPiece = currentPiece
        
        // Generate new next piece
        spawnNextPiece()
        
        // Reset held piece flag
        hasHeld = false
        gameState.hasHeld = hasHeld
        
        // Position the piece at the top-center of the board
        val piece = currentPiece!!
        piece.x = (boardWidth - 4) / 2
        piece.y = 0
        
        // Check for game over (collision on spawn)
        if (board.isCollision(piece)) {
            isGameOver = true
            gameState.isGameOver = true
            callback.onGameOver(gameState)
            return
        }
        
        // Reset lock delay
        lockDelayTimer = 0
        
        // Update the rendering
        callback.onRender(board, currentPiece, nextPiece, heldPiece)
    }
    
    /**
     * Locks the current piece in place and checks for line clears
     */
    private fun lockPiece() {
        val piece = currentPiece ?: return
        
        // Add the piece to the board
        board.addPiece(piece)
        
        // Update piece statistics
        gameState.updatePiecePlacement(piece)
        
        // Check for cleared lines
        val linesCleared = board.clearLines()
        
        // Check for T-spin
        val isTSpin = checkTSpin(piece)
        
        // Check for perfect clear
        val isPerfectClear = isPerfectClear()
        
        // Update game state after line clear
        gameState.updateAfterLineClear(linesCleared, isTSpin, isPerfectClear)
        
        // Update drop speed based on new level
        dropInterval = calculateDropInterval(gameState.level)
        
        // Reset the current piece
        currentPiece = null
        
        // Update the game state
        callback.onLinesCleared(linesCleared)
        callback.onScoreChanged(gameState.score, gameState.level)
        
        // Check for game over condition
        if (board.isGameOver()) {
            isGameOver = true
            gameState.isGameOver = true
            callback.onGameOver(gameState)
        }
    }
    
    /**
     * Checks if the last move was a T-spin
     */
    private fun checkTSpin(piece: Piece): Boolean {
        // T-spin detection: Check if it's a T piece and 3 of the 4 corners around the center are filled
        if (piece.type == PieceType.T) {
            var cornersOccupied = 0
            
            // Define the four corners around T center
            val corners = arrayOf(
                Point(-1, -1),
                Point(1, -1),
                Point(-1, 1),
                Point(1, 1)
            )
            
            // Check each corner
            for (corner in corners) {
                val x = piece.x + 1 + corner.x // +1 because T center is at (1,1) relative to piece origin
                val y = piece.y + 1 + corner.y
                
                // Check if corner is outside board or occupied
                if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight || (y >= 0 && board.cells[y][x] != null)) {
                    cornersOccupied++
                }
            }
            
            return cornersOccupied >= 3
        }
        
        return false
    }
    
    /**
     * Checks if the board is completely empty (perfect clear)
     */
    private fun isPerfectClear(): Boolean {
        for (y in 0 until boardHeight) {
            for (x in 0 until boardWidth) {
                if (board.cells[y][x] != null) {
                    return false
                }
            }
        }
        return true
    }
    
    /**
     * Calculates the drop interval based on the current level
     * Formula: (0.8 - ((level - 1) * 0.007))^(level - 1) * 1000
     */
    private fun calculateDropInterval(level: Int): Long {
        // Clamp level between 1 and 20 for drop speed calculation
        val clampedLevel = max(1, min(level, 20))
        val exponent = clampedLevel - 1
        val base = 0.8 - ((clampedLevel - 1) * 0.007)
        val gravity = Math.pow(base, exponent.toDouble())
        return (gravity * 1000).toLong()
    }
    
    /**
     * Moves the current piece left if possible
     * @return true if the move was successful
     */
    fun movePieceLeft(): Boolean {
        val piece = currentPiece ?: return false
        
        if (board.canMove(piece, -1, 0)) {
            piece.x--
            gameState.updateMovementStats(MoveType.LEFT)
            lockDelayTimer = 0 // Reset lock delay on movement
            callback.onRender(board, currentPiece, nextPiece, heldPiece)
            return true
        }
        
        return false
    }
    
    /**
     * Moves the current piece right if possible
     * @return true if the move was successful
     */
    fun movePieceRight(): Boolean {
        val piece = currentPiece ?: return false
        
        if (board.canMove(piece, 1, 0)) {
            piece.x++
            gameState.updateMovementStats(MoveType.RIGHT)
            lockDelayTimer = 0 // Reset lock delay on movement
            callback.onRender(board, currentPiece, nextPiece, heldPiece)
            return true
        }
        
        return false
    }
    
    /**
     * Moves the current piece down if possible
     * @return true if the move was successful
     */
    fun movePieceDown(): Boolean {
        val piece = currentPiece ?: return false
        
        if (board.canMove(piece, 0, 1)) {
            piece.y++
            gameState.updateMovementStats(MoveType.SOFT_DROP)
            callback.onRender(board, currentPiece, nextPiece, heldPiece)
            return true
        }
        
        return false
    }
    
    /**
     * Rotates the current piece clockwise if possible
     * @return true if the rotation was successful
     */
    fun rotatePieceClockwise(): Boolean {
        val piece = currentPiece ?: return false
        
        if (board.tryRotate(piece, true)) {
            gameState.updateMovementStats(MoveType.ROTATE)
            lockDelayTimer = 0 // Reset lock delay on rotation
            callback.onRender(board, currentPiece, nextPiece, heldPiece)
            return true
        }
        
        return false
    }
    
    /**
     * Rotates the current piece counter-clockwise if possible
     * @return true if the rotation was successful
     */
    fun rotatePieceCounterClockwise(): Boolean {
        val piece = currentPiece ?: return false
        
        if (board.tryRotate(piece, false)) {
            gameState.updateMovementStats(MoveType.ROTATE)
            lockDelayTimer = 0 // Reset lock delay on rotation
            callback.onRender(board, currentPiece, nextPiece, heldPiece)
            return true
        }
        
        return false
    }
    
    /**
     * Performs a hard drop on the current piece
     * @return the number of cells dropped
     */
    fun hardDrop(): Int {
        val piece = currentPiece ?: return 0
        var distance = 0
        
        // Move the piece down as far as possible
        while (board.canMove(piece, 0, 1)) {
            piece.y++
            distance++
        }
        
        // Update statistics
        gameState.updateMovementStats(MoveType.HARD_DROP)
        
        // Lock the piece immediately
        lockPiece()
        
        // Give bonus points for hard drop
        val dropPoints = distance * 2L
        gameState.score += dropPoints
        callback.onScoreChanged(gameState.score, gameState.level)
        
        return distance
    }
    
    /**
     * Holds the current piece and swaps it with the held piece
     * @return true if the hold was successful
     */
    fun holdPiece(): Boolean {
        if (hasHeld || currentPiece == null) {
            return false
        }
        
        gameState.updateMovementStats(MoveType.HOLD)
        
        val tempPiece = currentPiece!!
        
        if (heldPiece == null) {
            // First hold, spawn next piece
            currentPiece = null
            spawnPiece()
        } else {
            // Swap with held piece
            currentPiece = heldPiece
            currentPiece?.let {
                // Reset position to top-center
                it.x = (boardWidth - 4) / 2
                it.y = 0
                it.rotation = 0
            }
        }
        
        // Store the previously active piece
        heldPiece = Piece(tempPiece.type)
        gameState.heldPiece = heldPiece
        
        // Mark that hold has been used for this turn
        hasHeld = true
        gameState.hasHeld = true
        
        // Reset lock delay
        lockDelayTimer = 0
        
        callback.onRender(board, currentPiece, nextPiece, heldPiece)
        return true
    }
    
    /**
     * Gets the current game state
     */
    fun getGameState(): GameState {
        return gameState
    }
    
    /**
     * Loads a saved game state
     */
    fun loadGameState(savedState: GameState) {
        this.gameState = savedState
        
        // Update local references
        board.clear()
        for (y in 0 until boardHeight) {
            for (x in 0 until boardWidth) {
                board.cells[y][x] = savedState.board.cells[y][x]
            }
        }
        
        currentPiece = savedState.currentPiece
        nextPiece = savedState.nextPiece
        heldPiece = savedState.heldPiece
        hasHeld = savedState.hasHeld
        isGameOver = savedState.isGameOver
        isPaused = savedState.isPaused
        timeElapsed = savedState.timeElapsed
        
        // Update drop interval based on loaded level
        dropInterval = calculateDropInterval(savedState.level)
        
        // Reset timing variables
        gameStartTime = SystemClock.uptimeMillis() - timeElapsed
        lastFrameTime = SystemClock.uptimeMillis()
        
        // Tell the callback about the loaded state
        callback.onScoreChanged(gameState.score, gameState.level)
        callback.onRender(board, currentPiece, nextPiece, heldPiece)
    }
    
    companion object {
        // Constants
        private const val FRAME_DELAY = 16L // ~60 FPS
        private const val DEFAULT_LOCK_DELAY = 500L // 500ms
    }
    
    /**
     * Callback interface for the Tetris engine
     */
    interface TetrisCallback {
        fun onGameStart()
        fun onGamePause()
        fun onGameResume()
        fun onGameStop()
        fun onGameOver(gameState: GameState)
        fun onGameWin()
        fun onLinesCleared(lines: Int)
        fun onScoreChanged(score: Long, level: Int)
        fun onRender(board: Board, currentPiece: Piece?, nextPiece: Piece?, heldPiece: Piece?)
    }
}
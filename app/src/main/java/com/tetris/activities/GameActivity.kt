package com.tetris.activities

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tetris.R
import com.tetris.data.AppDatabase
import com.tetris.game.GameView
import com.tetris.game.InputManager
import com.tetris.game.TetrisEngine
import com.tetris.model.Board
import com.tetris.model.GameMode
import com.tetris.model.GameState
import com.tetris.model.Piece
import com.tetris.model.Player
import com.tetris.utils.BatteryOptimizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity for the main Tetris gameplay screen.
 * Integrates TetrisEngine, GameView, and InputManager components.
 */
class GameActivity : AppCompatActivity(), TetrisEngine.TetrisCallback, InputManager.InputListener {
    // UI components
    private lateinit var gameView: GameView
    private lateinit var scoreTextView: TextView
    private lateinit var levelTextView: TextView
    private lateinit var linesTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var pauseButton: ImageButton

    // Game components
    private lateinit var tetrisEngine: TetrisEngine
    private lateinit var inputManager: InputManager

    // Battery and performance optimization
    private lateinit var batteryOptimizer: BatteryOptimizer

    // Database
    private lateinit var db: AppDatabase

    // State variables
    private var gameMode = GameMode.MARATHON
    private var continueGame = false
    private var playerId = 0
    private var isGameActive = false
    private var isPaused = false
    private var gameTimeMillis = 0L
    private var accelerometerWasEnabled = false


    // Timer for updating the clock display
    private val timeHandler = Handler(Looper.getMainLooper())
    private val timeRunnable = object : Runnable {
        override fun run() {
            if (isGameActive && !isPaused) {
                updateTimeDisplay()
                timeHandler.postDelayed(this, 1000) // Update every second
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep screen on during gameplay
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_game)

        // Get game parameters from intent
        parseIntent()

        // Initialize database
        db = AppDatabase.getInstance(this)

        // Initialize UI components
        initializeViews()

        // Initialize game components
        initializeGameComponents()

        // Set up input handling
        setupInputHandling()

        // Start/resume game
        if (continueGame) {
            loadSavedGame()
        } else {
            startNewGame()
        }
    }

    /**
     * Parse intent extras
     */
    private fun parseIntent() {
        intent?.extras?.let { extras ->
            // Get game mode
            val gameModeStr = extras.getString(EXTRA_GAME_MODE, GameMode.MARATHON.name)
            gameMode = try {
                GameMode.valueOf(gameModeStr)
            } catch (e: IllegalArgumentException) {
                GameMode.MARATHON
            }

            // Check if continuing a saved game
            continueGame = extras.getBoolean(EXTRA_CONTINUE_GAME, false)

            // Get player ID
            playerId = extras.getInt(EXTRA_PLAYER_ID, 0)
        }
    }

    /**
     * Initialize UI views
     */
    private fun initializeViews() {
        gameView = findViewById(R.id.gameView)
        scoreTextView = findViewById(R.id.scoreTextView)
        levelTextView = findViewById(R.id.levelTextView)
        linesTextView = findViewById(R.id.linesTextView)
        timeTextView = findViewById(R.id.timeTextView)
        pauseButton = findViewById(R.id.pauseButton)

        // Set initial values
        scoreTextView.text = "0"
        levelTextView.text = "1"
        linesTextView.text = "0"
        timeTextView.text = "00:00"

        // Set up button listeners
        pauseButton.setOnClickListener {
            togglePause()
        }
    }

    /**
     * Initialize game engine and related components
     */
    private fun initializeGameComponents() {
        // Create battery optimizer
        batteryOptimizer = BatteryOptimizer(this)

        // Create the game engine with standard board size (10x20)
        tetrisEngine = TetrisEngine(
            boardWidth = 10,
            boardHeight = 20,
            callback = this,
            initialLevel = 1,
            gameMode = gameMode
        )

        // Set up game view with the same board size
        gameView.setBoardSize(10, 20)

        // Apply performance optimizations based on battery level
        applyPerformanceOptimizations()
    }

    /**
     * Set up input handling
     */
    private fun setupInputHandling() {
        // Create input manager
        inputManager = InputManager(this, this)

        // Set touch listener on game view
        gameView.setOnTouchListener(inputManager)

        // Enable accelerometer if set in preferences
        accelerometerWasEnabled = getSharedPreferences("tetris_settings", MODE_PRIVATE)
            .getBoolean("use_accelerometer", false)
        inputManager.setAccelerometerEnabled(accelerometerWasEnabled)

        // Set sensitivity from preferences
        val sensitivity = getSharedPreferences("tetris_settings", MODE_PRIVATE)
            .getFloat("input_sensitivity", 0.5f)
        inputManager.setSensitivity(sensitivity)
    }

    /**
     * Apply performance optimizations based on battery level
     */
    private fun applyPerformanceOptimizations() {
        // Get battery level
        val batteryLevel = batteryOptimizer.getBatteryLevel()

        // Set rendering options based on battery level
        val showGridLines = batteryLevel > 30 // Only show grid lines if battery > 30%
        val showGhostPiece = batteryLevel > 20 // Only show ghost piece if battery > 20%
        val useThemeColors = batteryLevel > 15 // Use simplified colors if battery very low

        gameView.setRenderOptions(showGridLines, showGhostPiece, useThemeColors)
    }

    /**
     * Start a new game
     */
    private fun startNewGame() {
        isGameActive = true
        isPaused = false
        tetrisEngine.start()
        timeHandler.post(timeRunnable)
    }

    /**
     * Load a saved game
     */
    private fun loadSavedGame() {
        lifecycleScope.launch {
            // Load game state from database
            val gameState = withContext(Dispatchers.IO) {
                db.gameStateDao().getActiveGameStateForPlayer(playerId)
            }

            if (gameState != null) {
                // Update UI with saved game info
                withContext(Dispatchers.Main) {
                    scoreTextView.text = gameState.score.toString()
                    levelTextView.text = gameState.level.toString()
                    linesTextView.text = gameState.linesCleared.toString()
                    gameTimeMillis = gameState.timeElapsed
                    updateTimeDisplay()

                    // Load the game state into the engine
                    tetrisEngine.loadGameState(gameState)

                    // Start the game
                    isGameActive = true
                    isPaused = gameState.isPaused

                    // Update pause button state
                    pauseButton.setImageResource(
                        if (isPaused) R.drawable.ic_play else R.drawable.ic_pause
                    )

                    if (!isPaused) {
                        tetrisEngine.start()
                        timeHandler.post(timeRunnable)
                    }
                }
            } else {
                // No saved game found, start a new one
                withContext(Dispatchers.Main) {
                    startNewGame()
                }
            }
        }
    }

    /**
     * Toggle pause state
     */
    private fun togglePause() {
        if (isGameActive) {
            if (isPaused) {
                // Resume game
                isPaused = false
                tetrisEngine.resume()
                pauseButton.setImageResource(R.drawable.ic_pause)
                timeHandler.post(timeRunnable)
            } else {
                // Pause game
                isPaused = true
                tetrisEngine.pause()
                pauseButton.setImageResource(R.drawable.ic_play)
                timeHandler.removeCallbacks(timeRunnable)
            }
        }
    }

    /**
     * Update the time display
     */
    private fun updateTimeDisplay() {
        // Update game time
        gameTimeMillis = tetrisEngine.getGameState().timeElapsed

        // Format the time as MM:SS
        val totalSeconds = gameTimeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        timeTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    /**
     * Save the current game state
     */
    private fun saveGameState() {
        lifecycleScope.launch {
            if (isGameActive && playerId > 0) {
                val currentGameState: GameState = tetrisEngine.getGameState()
                // Create a new GameState instance with updated playerId and isPaused
                val updatedGameState = currentGameState.copy(
                    playerId = this@GameActivity.playerId, // GameActivity's playerId
                    isPaused = this@GameActivity.isPaused  // GameActivity's isPaused
                )

                // Save to database
                withContext(Dispatchers.IO) {
                    db.gameStateDao().insertOrUpdate(updatedGameState)
                }
            }
        }
    }

    /**
     * Update player statistics with the current game state
     */
    private fun updatePlayerStats(gameState: GameState) {
        lifecycleScope.launch {
            if (playerId > 0) {
                withContext(Dispatchers.IO) {
                    // Get current player
                    val player = db.playerDao().getPlayerById(playerId)

                    player?.let {
                        // Extract stats from game state
                        val stats = gameState.extractStatistics()

                        // Update player stats
                        it.updateStats(
                            score = stats["score"] as Long,
                            linesCleared = stats["linesCleared"] as Int,
                            level = stats["level"] as Int,
                            timePlayed = stats["timeElapsed"] as Long,
                            tetrisCount = stats["tetrisCount"] as Int,
                            perfectClear = (stats["perfectClearCount"] as Int) > 0
                        )

                        // Save updated player
                        db.playerDao().update(it)

                        // Update statistics table
                        val statistics = db.statisticsDao().getStatisticsForPlayer(player.id)
                        if (statistics != null) {
                            statistics.updateAfterGame(
                                score = stats["score"] as Long,
                                linesCleared = stats["linesCleared"] as Int,
                                level = stats["level"] as Int,
                                timePlayed = stats["timeElapsed"] as Long,
                                piecesPlaced = stats["totalPiecesPlaced"] as Int,
                                singles = stats["singleLines"] as Int,
                                doubles = stats["doubleLines"] as Int,
                                triples = stats["tripleLines"] as Int,
                                tetris = stats["tetrisCount"] as Int,
                                tSpins = stats["tSpins"] as Int,
                                tSpinSingles = stats["tSpinSingles"] as Int,
                                tSpinDoubles = stats["tSpinDoubles"] as Int,
                                tSpinTriples = stats["tSpinTriples"] as Int,
                                perfectClears = stats["perfectClearCount"] as Int,
                                maxCombo = stats["maxCombo"] as Int,
                                backToBack = stats["backToBack"] as Int,
                                iPieces = stats["iPiecesPlaced"] as Int,
                                jPieces = stats["jPiecesPlaced"] as Int,
                                lPieces = stats["lPiecesPlaced"] as Int,
                                oPieces = stats["oPiecesPlaced"] as Int,
                                sPieces = stats["sPiecesPlaced"] as Int,
                                tPieces = stats["tPiecesPlaced"] as Int,
                                zPieces = stats["zPiecesPlaced"] as Int,
                                rotations = stats["rotations"] as Int,
                                leftMoves = stats["movesLeft"] as Int,
                                rightMoves = stats["movesRight"] as Int,
                                softDrops = stats["softDropCount"] as Int,
                                hardDrops = stats["hardDropCount"] as Int,
                                holdCount = stats["holdPieceCount"] as Int,
                                won = gameMode != GameMode.MARATHON && !gameState.isGameOver
                            )
                            db.statisticsDao().update(statistics)
                        }
                    }
                }
            }
        }
    }

    /**
     * Show game over dialog
     */
    private fun showGameOverDialog(gameState: GameState) {
        val score = gameState.score
        val level = gameState.level
        val linesCleared = gameState.linesCleared

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over")
        builder.setMessage(
            "Score: $score\n" +
                    "Level: $level\n" +
                    "Lines: $linesCleared\n" +
                    "Time: ${formatTime(gameState.timeElapsed)}"
        )

        builder.setPositiveButton("New Game") { _, _ ->
            // Start a new game
            startNewGame()
        }

        builder.setNegativeButton("Main Menu") { _, _ ->
            // Return to main menu
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }

    /**
     * Show game win dialog (for Sprint and Ultra modes)
     */
    private fun showGameWinDialog() {
        val gameState = tetrisEngine.getGameState()
        val score = gameState.score
        val linesCleared = gameState.linesCleared
        val time = gameState.timeElapsed

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Congratulations!")

        val message = when (gameMode) {
            GameMode.SPRINT -> {
                "You cleared 40 lines in ${formatTime(time)}!\n" +
                        "Score: $score"
            }
            GameMode.ULTRA -> {
                "Time's up! You scored $score points in 2 minutes!\n" +
                        "Lines cleared: $linesCleared"
            }
            else -> {
                "You won!\n" +
                        "Score: $score\n" +
                        "Lines: $linesCleared\n" +
                        "Time: ${formatTime(time)}"
            }
        }

        builder.setMessage(message)

        builder.setPositiveButton("New Game") { _, _ ->
            // Start a new game
            startNewGame()
        }

        builder.setNegativeButton("Main Menu") { _, _ ->
            // Return to main menu
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }

    /**
     * Format time in milliseconds to MM:SS format
     */
    private fun formatTime(timeMillis: Long): String {
        val totalSeconds = timeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    //
    // TetrisEngine.TetrisCallback implementation
    //

    override fun onGameStart() {
        // Game has started
        isGameActive = true
        isPaused = false
    }

    override fun onGamePause() {
        // Game was paused
        isPaused = true
    }

    override fun onGameResume() {
        // Game was resumed
        isPaused = false
    }

    override fun onGameStop() {
        // Game was stopped
        isGameActive = false
        timeHandler.removeCallbacks(timeRunnable)
    }

    override fun onGameOver(gameState: GameState) {
        // Game is over
        runOnUiThread {
            isGameActive = false
            timeHandler.removeCallbacks(timeRunnable)
            updatePlayerStats(gameState)
            showGameOverDialog(gameState)
        }
    }

    override fun onGameWin() {
        // Game was won (Sprint or Ultra mode)
        runOnUiThread {
            isGameActive = false
            timeHandler.removeCallbacks(timeRunnable)
            updatePlayerStats(tetrisEngine.getGameState())
            showGameWinDialog()
        }
    }

    override fun onLinesCleared(lines: Int) {
        // Update lines display
        linesTextView.text = tetrisEngine.getGameState().linesCleared.toString()
    }

    override fun onScoreChanged(score: Long, level: Int) {
        // Update score and level display
        scoreTextView.text = score.toString()
        levelTextView.text = level.toString()
    }

    override fun onRender(board: Board, currentPiece: Piece?, nextPiece: Piece?, heldPiece: Piece?) {
        // Update game view with new state
        gameView.updateGameState(board, currentPiece, nextPiece, heldPiece)
        // gameView.invalidate() // Request redraw - updateGameState should call render which invalidates
    }

    //
    // InputManager.InputListener implementation
    //

    override fun onMove(direction: InputManager.InputDirection) {
        if (isGameActive && !isPaused) {
            when (direction) {
                InputManager.InputDirection.LEFT -> tetrisEngine.movePieceLeft()
                InputManager.InputDirection.RIGHT -> tetrisEngine.movePieceRight()
                InputManager.InputDirection.DOWN -> tetrisEngine.movePieceDown()
                // UP is not handled by onMove in this setup
            }
        }
    }

    override fun onRotate(clockwise: Boolean) {
        if (isGameActive && !isPaused) {
            if (clockwise) {
                tetrisEngine.rotatePieceClockwise()
            } else {
                tetrisEngine.rotatePieceCounterClockwise()
            }
        }
    }

    override fun onSoftDrop(pressed: Boolean) {
        // This is called when a scroll down event occurs.
        // The 'pressed' state can be used for continuous soft drop if desired.
        // For now, a single call to movePieceDown is sufficient.
        if (isGameActive && !isPaused && pressed) {
            tetrisEngine.movePieceDown()
        }
    }

    override fun onReleaseDown() {
        // This is called when a touch up event occurs after a scroll down.
        // Can be used to stop continuous soft drop if implemented.
        // For now, no specific action needed here.
    }

    override fun onHardDrop() {
        if (isGameActive && !isPaused) {
            tetrisEngine.hardDrop()
        }
    }

    override fun onHold() {
        if (isGameActive && !isPaused) {
            tetrisEngine.holdPiece()
        }
    }

    //
    // Activity lifecycle methods
    //

    override fun onPause() {
        super.onPause()
        if (isGameActive) {
            isPaused = true
            tetrisEngine.pause()
            pauseButton.setImageResource(R.drawable.ic_play)
            timeHandler.removeCallbacks(timeRunnable)
            saveGameState() // Save game when activity is paused
            if (accelerometerWasEnabled) {
                inputManager.setAccelerometerEnabled(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isGameActive && isPaused) {
            // Don't auto-resume if it was explicitly paused by user
            // User needs to press play button
        } else if (isGameActive && !isPaused) {
            // If game was active and not paused (e.g. screen lock), resume engine
            tetrisEngine.resume()
            timeHandler.post(timeRunnable)
        }
        if (accelerometerWasEnabled) {
            inputManager.setAccelerometerEnabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isGameActive) {
            tetrisEngine.stop() // Ensure engine is stopped
            saveGameState()     // Save game on destroy
        }
        inputManager.dispose() // Clean up InputManager resources
        timeHandler.removeCallbacks(timeRunnable)
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    companion object {
        const val EXTRA_GAME_MODE = "game_mode"
        const val EXTRA_CONTINUE_GAME = "continue_game"
        const val EXTRA_PLAYER_ID = "player_id"
    }
}
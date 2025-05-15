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
        val useAccelerometer = getSharedPreferences("tetris_settings", MODE_PRIVATE)
            .getBoolean("use_accelerometer", false)
        inputManager.setAccelerometerEnabled(useAccelerometer)
        
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
                val gameState = tetrisEngine.getGameState()
                gameState.playerId = playerId
                gameState.isPaused = isPaused
                
                // Save to database
                withContext(Dispatchers.IO) {
                    db.gameStateDao().insertOrUpdate(gameState)
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
            
            // Update player statistics
            updatePlayerStats(gameState)
            
            // Remove saved game state since it's complete
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    db.gameStateDao().deleteByPlayerId(playerId)
                }
            }
            
            // Show game over dialog
            showGameOverDialog(gameState)
        }
    }
    
    override fun onGameWin() {
        // Player won the game (Sprint or Ultra mode)
        runOnUiThread {
            isGameActive = false
            timeHandler.removeCallbacks(timeRunnable)
            
            // Update player statistics
            updatePlayerStats(tetrisEngine.getGameState())
            
            // Remove saved game state since it's complete
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    db.gameStateDao().deleteByPlayerId(playerId)
                }
            }
            
            // Show win dialog
            showGameWinDialog()
        }
    }
    
    override fun onLinesCleared(lines: Int) {
        // Lines were cleared, update UI
        runOnUiThread {
            linesTextView.text = tetrisEngine.getGameState().linesCleared.toString()
        }
    }
    
    override fun onScoreChanged(score: Long, level: Int) {
        // Score or level changed, update UI
        runOnUiThread {
            scoreTextView.text = score.toString()
            levelTextView.text = level.toString()
        }
    }
    
    override fun onRender(board: Board, currentPiece: Piece?, nextPiece: Piece?, heldPiece: Piece?) {
        // Update the game view with current game state
        gameView.updateGameState(board, currentPiece, nextPiece, heldPiece)
        
        // Update metrics display
        val state = tetrisEngine.getGameState()
        gameView.updateMetrics(state.score, state.level, state.linesCleared)
    }
    
    //
    // InputManager.InputListener implementation
    //
    
    override fun onMove(direction: InputManager.InputDirection) {
        if (!isGameActive || isPaused) return
        
        when (direction) {
            InputManager.InputDirection.LEFT -> tetrisEngine.movePieceLeft()
            InputManager.InputDirection.RIGHT -> tetrisEngine.movePieceRight()
            InputManager.InputDirection.DOWN -> tetrisEngine.movePieceDown()
        }
    }
    
    override fun onRotate(clockwise: Boolean) {
        if (!isGameActive || isPaused) return
        
        if (clockwise) {
            tetrisEngine.rotatePieceClockwise()
        } else {
            tetrisEngine.rotatePieceCounterClockwise()
        }
    }
    
    override fun onSoftDrop(pressed: Boolean) {
        if (!isGameActive || isPaused) return
        
        if (pressed) {
            // Move down once, actual continuous soft drop is handled by the engine
            tetrisEngine.movePieceDown()
        }
    }
    
    override fun onReleaseDown() {
        // Nothing to do here, soft drop is handled by single moves
    }
    
    override fun onHardDrop() {
        if (!isGameActive || isPaused) return
        
        tetrisEngine.hardDrop()
    }
    
    override fun onHold() {
        if (!isGameActive || isPaused) return
        
        tetrisEngine.holdPiece()
    }
    
    override fun onPause() {
        if (isGameActive) {
            togglePause()
        }
    }
    
    //
    // Activity lifecycle methods
    //
    
    override fun onPause() {
        super.onPause()
        
        // If game is active but not already paused, pause it
        if (isGameActive && !isPaused) {
            tetrisEngine.pause()
            isPaused = true
        }
        
        // Save game state
        saveGameState()
        
        // Disable accelerometer to save battery
        inputManager.setAccelerometerEnabled(false)
        
        // Remove time handler callbacks
        timeHandler.removeCallbacks(timeRunnable)
    }
    
    override fun onResume() {
        super.onResume()
        
        // Re-apply battery optimizations in case battery level changed
        applyPerformanceOptimizations()
        
        // If using accelerometer, re-enable it
        val useAccelerometer = getSharedPreferences("tetris_settings", MODE_PRIVATE)
            .getBoolean("use_accelerometer", false)
        inputManager.setAccelerometerEnabled(useAccelerometer)
        
        // Don't automatically resume the game - leave it paused until user presses play
        if (isGameActive && isPaused) {
            pauseButton.setImageResource(R.drawable.ic_play)
        } else if (isGameActive) {
            timeHandler.post(timeRunnable)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Clean up resources
        tetrisEngine.stop()
        inputManager.dispose()
        timeHandler.removeCallbacks(timeRunnable)
        
        // Save final game state if game is still active
        if (isGameActive) {
            saveGameState()
        }
    }
    
    companion object {
        // Intent extras
        const val EXTRA_GAME_MODE = "extra_game_mode"
        const val EXTRA_CONTINUE_GAME = "extra_continue_game"
        const val EXTRA_PLAYER_ID = "extra_player_id"
    }
}
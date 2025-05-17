package com.tetris.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tetris.R
import com.tetris.model.GameMode
import com.tetris.model.Player
import com.tetris.data.AppDatabase
import com.tetris.activities.AchievementsActivity // Added import
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Main activity serving as the entry point and main menu for the Tetris game.
 */
class MainActivity : AppCompatActivity() {
    // UI elements
    private lateinit var logoImageView: ImageView
    private lateinit var startButton: Button
    private lateinit var continueButton: Button
    private lateinit var settingsButton: Button
    private lateinit var statisticsButton: Button
    private lateinit var themesButton: Button
    private lateinit var achievementsButton: Button
    private lateinit var highScoreTextView: TextView
    private lateinit var playerNameTextView: TextView
    
    // Database and player data
    private lateinit var db: AppDatabase
    private var currentPlayer: Player? = null
    
private var activeGamePlayerId: Int? = null // Cache player ID for whom an active game exists
    // Coroutine scope for DB operations
    private val dbScope = CoroutineScope(Dispatchers.IO)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize database
        db = AppDatabase.getInstance(this)
        
        // Initialize UI elements
        initializeViews()
        setupListeners()
        
        // Load player data
        loadPlayerData()
        
        // Animate the logo
        animateLogo()
        
        // Check for saved game
        checkForSavedGame()
    }
    
    /**
     * Initialize UI views
     */
    private fun initializeViews() {
        logoImageView = findViewById(R.id.logoImageView)
        startButton = findViewById(R.id.startButton)
        continueButton = findViewById(R.id.continueButton)
        settingsButton = findViewById(R.id.settingsButton)
        statisticsButton = findViewById(R.id.statisticsButton)
        themesButton = findViewById(R.id.themesButton)
        achievementsButton = findViewById(R.id.achievementsButton)
        highScoreTextView = findViewById(R.id.highScoreTextView)
        playerNameTextView = findViewById(R.id.playerNameTextView)
        
        // Initially hide continue button until we check if there's a saved game
        continueButton.visibility = View.GONE
    }
    
    /**
     * Set up click listeners for buttons
     */
    private fun setupListeners() {
        startButton.setOnClickListener {
            showGameModeDialog()
        }
        
        continueButton.setOnClickListener {
            activeGamePlayerId?.let { playerId ->
                Log.d("MainActivity", "Continue button clicked with activeGamePlayerId: $playerId")
                startGame(GameMode.MARATHON, true, playerId)
            } ?: run {
                // Fallback or error handling if activeGamePlayerId is null, though button shouldn't be visible
                Log.e("MainActivity", "Continue button clicked but activeGamePlayerId is null. This shouldn't happen if button is visible.")
                // Optionally, start a new game or show an error
                 startGame(GameMode.MARATHON, false) // Or show an error to the user
            }
        }
        
        settingsButton.setOnClickListener {
            // Start settings activity
            val intent = Intent(this, SettingsActivity::class.java) // TODO: Uncomment when SettingsActivity is created
            startActivity(intent)
        }

        statisticsButton.setOnClickListener {
            // Start statistics activity
            val intent = Intent(this, StatisticsActivity::class.java) // TODO: Uncomment when StatisticsActivity is created
            startActivity(intent)
        }

        themesButton.setOnClickListener {
            // Start themes activity
            val intent = Intent(this, ThemeActivity::class.java) // TODO: Uncomment when ThemeActivity is created
            startActivity(intent)
        }

        achievementsButton.setOnClickListener {
            // Start achievements activity
            val intent = Intent(this, AchievementsActivity::class.java) // TODO: Uncomment when AchievementsActivity is created
            startActivity(intent)
        }
    }
    
    /**
     * Show dialog to select game mode
     */
    private fun showGameModeDialog() {
        val dialog = GameModeDialog(this) { gameMode ->
            startGame(gameMode, false, currentPlayer?.id)
        }
        dialog.show()
    }
    
    /**
     * Start the game with the selected mode
     */
    private fun startGame(gameMode: GameMode, continueGame: Boolean, explicitPlayerId: Int? = null) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra(GameActivity.EXTRA_GAME_MODE, gameMode.name)
            putExtra(GameActivity.EXTRA_CONTINUE_GAME, continueGame)

            val playerIdToUse = explicitPlayerId ?: currentPlayer?.id

            if (playerIdToUse != null) {
                putExtra(GameActivity.EXTRA_PLAYER_ID, playerIdToUse)
                Log.d("MainActivity", "Starting GameActivity with playerId: $playerIdToUse, continueGame: $continueGame")
            } else {
                Log.w("MainActivity", "Starting GameActivity without a playerId. New game will likely be forced if continueGame is true.")
            }
        }
        startActivity(intent)
    }
    
    /**
     * Load player data from the database
     */
    private fun loadPlayerData() {
        dbScope.launch {
            // Get the most recent player, or create a default one if none exists
            val player = db.playerDao().getLastActivePlayer()
            
            if (player != null) {
                currentPlayer = player
                
                // Update UI with player data on the main thread
                withContext(Dispatchers.Main) {
                    playerNameTextView.text = player.name
                    highScoreTextView.text = "High Score: ${player.highScore}"
                }
            } else {
                // Create a default player
                val defaultPlayer = Player.createDefault("Player 1")
                val playerId = db.playerDao().insert(defaultPlayer).toInt()
                
                // Retrieve the player with the assigned ID
                val newPlayer = db.playerDao().getPlayerById(playerId)
                currentPlayer = newPlayer
                
                // Update UI with default player data
                withContext(Dispatchers.Main) {
                    playerNameTextView.text = defaultPlayer.name
                    highScoreTextView.text = "High Score: 0"
                }
            }
        }
    }
    
    /**
     * Check if there's a saved game to continue
     */
    private fun checkForSavedGame() {
        dbScope.launch {
            // Check if there's an active game
            var hasActiveGame = false
            val currentLoadedPlayer = currentPlayer // Capture current player state for this check
            
            if (currentLoadedPlayer != null) {
                val activeGameState = db.gameStateDao().getActiveGameStateForPlayer(currentLoadedPlayer.id)
                if (activeGameState != null) {
                    activeGamePlayerId = currentLoadedPlayer.id // Cache the ID
                    hasActiveGame = true
                    Log.d("MainActivity", "Active game found for playerId: ${currentLoadedPlayer.id}. Continue button visible.")
                } else {
                    activeGamePlayerId = null // No active game for this player
                    Log.d("MainActivity", "No active game found for playerId: ${currentLoadedPlayer.id}. Continue button hidden.")
                }
            } else {
                activeGamePlayerId = null // No player loaded
                Log.d("MainActivity", "No current player loaded. Continue button hidden.")
            }
            
            // Update UI to show continue button if there's an active game
            withContext(Dispatchers.Main) {
                continueButton.visibility = if (hasActiveGame) View.VISIBLE else View.GONE
            }
        }
    }
    
    /**
     * Animate the logo
     */
    private fun animateLogo() {
        val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        logoImageView.startAnimation(pulseAnimation)
    }
    
    /**
     * Resume activity lifecycle
     */
    override fun onResume() {
        super.onResume()
        
        // Refresh player data and check for saved games
        loadPlayerData()
        checkForSavedGame()
    }
}

/**
 * Dialog for selecting game mode
 */
class GameModeDialog(
    private val activity: MainActivity,
    private val onGameModeSelected: (GameMode) -> Unit
) {
    private lateinit var dialog: androidx.appcompat.app.AlertDialog
    
    fun show() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(activity)
        builder.setTitle("Select Game Mode")
        
        val modes = arrayOf(
            "Marathon" to "Classic endless mode",
            "Sprint" to "Clear 40 lines as fast as possible",
            "Ultra" to "Score as many points as possible in 2 minutes",
            "Battle" to "Play against the AI or another player",
            "Practice" to "Practice at your own pace"
        )
        
        val items = modes.map { "${it.first}: ${it.second}" }.toTypedArray()
        
        builder.setItems(items) { _, which ->
            val gameMode = when (which) {
                0 -> GameMode.MARATHON
                1 -> GameMode.SPRINT
                2 -> GameMode.ULTRA
                3 -> GameMode.BATTLE
                4 -> GameMode.PRACTICE
                else -> GameMode.MARATHON
            }
            onGameModeSelected(gameMode)
        }
        
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        
        dialog = builder.create()
        dialog.show()
    }
}
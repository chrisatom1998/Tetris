package com.tetris.activities

import android.content.Intent
import android.os.Bundle
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
            // Start the game with the saved state
            startGame(GameMode.MARATHON, true)
        }
        
        settingsButton.setOnClickListener {
            // Start settings activity
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        
        statisticsButton.setOnClickListener {
            // Start statistics activity
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
        
        themesButton.setOnClickListener {
            // Start themes activity
            val intent = Intent(this, ThemeActivity::class.java)
            startActivity(intent)
        }
        
        achievementsButton.setOnClickListener {
            // Start achievements activity
            val intent = Intent(this, AchievementsActivity::class.java)
            startActivity(intent)
        }
    }
    
    /**
     * Show dialog to select game mode
     */
    private fun showGameModeDialog() {
        val dialog = GameModeDialog(this) { gameMode ->
            startGame(gameMode, false)
        }
        dialog.show()
    }
    
    /**
     * Start the game with the selected mode
     */
    private fun startGame(gameMode: GameMode, continueGame: Boolean) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra(GameActivity.EXTRA_GAME_MODE, gameMode.name)
            putExtra(GameActivity.EXTRA_CONTINUE_GAME, continueGame)
            
            // Pass current player ID if available
            currentPlayer?.let {
                putExtra(GameActivity.EXTRA_PLAYER_ID, it.id)
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
            val hasActiveGame = currentPlayer?.let { player ->
                val activeGameState = db.gameStateDao().getActiveGameStateForPlayer(player.id)
                activeGameState != null
            } ?: false
            
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
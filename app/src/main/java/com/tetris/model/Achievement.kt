package com.tetris.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents an achievement that a player can unlock.
 * Achievements track various accomplishments in the game.
 */
@Entity(
    tableName = "achievements",
    foreignKeys = [
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // Foreign key to Player
    val playerId: Int,
    
    // ID referencing the achievement definition
    val achievementId: String,
    
    // Whether the achievement has been unlocked
    var isUnlocked: Boolean = false,
    
    // When the achievement was unlocked (null if not unlocked)
    var unlockedAt: Date? = null
)

/**
 * Entity representing achievement definitions (static data)
 */
@Entity(tableName = "achievement_definitions")
data class AchievementDefinition(
    @PrimaryKey
    val id: String,
    
    // Display name of the achievement
    val name: String,
    
    // Description of how to earn the achievement
    val description: String,
    
    // Category of the achievement
    val category: AchievementCategory,
    
    // Icon resource name
    val iconResource: String,
    
    // Progress required to unlock (for incremental achievements)
    val progressRequired: Int = 1,
    
    // Whether this achievement is secret (hidden until unlocked)
    val isSecret: Boolean = false,
    
    // Points value for this achievement
    val points: Int = 10
) {
    companion object {
        /**
         * Get all predefined achievements
         */
        fun getAllAchievements(): List<AchievementDefinition> {
            return listOf(
                // Game count achievements
                AchievementDefinition(
                    id = "games_1",
                    name = "First Steps",
                    description = "Play your first game of Tetris",
                    category = AchievementCategory.GAMES,
                    iconResource = "ic_achievement_first_game",
                    progressRequired = 1,
                    points = 5
                ),
                AchievementDefinition(
                    id = "games_10",
                    name = "Getting Serious",
                    description = "Play 10 games of Tetris",
                    category = AchievementCategory.GAMES,
                    iconResource = "ic_achievement_ten_games",
                    progressRequired = 10,
                    points = 10
                ),
                AchievementDefinition(
                    id = "games_50",
                    name = "Tetris Enthusiast",
                    description = "Play 50 games of Tetris",
                    category = AchievementCategory.GAMES,
                    iconResource = "ic_achievement_fifty_games",
                    progressRequired = 50,
                    points = 25
                ),
                
                // Score achievements
                AchievementDefinition(
                    id = "score_10k",
                    name = "Point Collector",
                    description = "Reach a score of 10,000 points",
                    category = AchievementCategory.SCORE,
                    iconResource = "ic_achievement_10k",
                    progressRequired = 10000,
                    points = 15
                ),
                AchievementDefinition(
                    id = "score_50k",
                    name = "High Scorer",
                    description = "Reach a score of 50,000 points",
                    category = AchievementCategory.SCORE,
                    iconResource = "ic_achievement_50k",
                    progressRequired = 50000,
                    points = 30
                ),
                AchievementDefinition(
                    id = "score_100k",
                    name = "Score Master",
                    description = "Reach a score of 100,000 points",
                    category = AchievementCategory.SCORE,
                    iconResource = "ic_achievement_100k",
                    progressRequired = 100000,
                    points = 50
                ),
                
                // Lines achievements
                AchievementDefinition(
                    id = "lines_100",
                    name = "Line Clearer",
                    description = "Clear 100 lines",
                    category = AchievementCategory.LINES,
                    iconResource = "ic_achievement_100_lines",
                    progressRequired = 100,
                    points = 15
                ),
                AchievementDefinition(
                    id = "lines_1k",
                    name = "Line Expert",
                    description = "Clear 1,000 lines",
                    category = AchievementCategory.LINES,
                    iconResource = "ic_achievement_1000_lines",
                    progressRequired = 1000,
                    points = 40
                ),
                
                // Level achievements
                AchievementDefinition(
                    id = "level_10",
                    name = "Speed Demon",
                    description = "Reach level 10",
                    category = AchievementCategory.LEVEL,
                    iconResource = "ic_achievement_level_10",
                    progressRequired = 10,
                    points = 20
                ),
                AchievementDefinition(
                    id = "level_20",
                    name = "Lightning Fast",
                    description = "Reach level 20",
                    category = AchievementCategory.LEVEL,
                    iconResource = "ic_achievement_level_20",
                    progressRequired = 20,
                    points = 35
                ),
                
                // Special moves achievements
                AchievementDefinition(
                    id = "first_tetris",
                    name = "Tetris!",
                    description = "Clear four lines at once",
                    category = AchievementCategory.SPECIAL,
                    iconResource = "ic_achievement_tetris",
                    progressRequired = 1,
                    points = 10
                ),
                AchievementDefinition(
                    id = "tetris_10",
                    name = "Tetris Master",
                    description = "Clear four lines at once 10 times",
                    category = AchievementCategory.SPECIAL,
                    iconResource = "ic_achievement_tetris_master",
                    progressRequired = 10,
                    points = 25
                ),
                AchievementDefinition(
                    id = "t_spin",
                    name = "Spin to Win",
                    description = "Perform your first T-Spin",
                    category = AchievementCategory.SPECIAL,
                    iconResource = "ic_achievement_tspin",
                    progressRequired = 1,
                    points = 15
                ),
                AchievementDefinition(
                    id = "perfect_clear",
                    name = "Perfect Clear",
                    description = "Clear all blocks from the board",
                    category = AchievementCategory.SPECIAL,
                    iconResource = "ic_achievement_perfect",
                    progressRequired = 1,
                    points = 30,
                    isSecret = true
                ),
                
                // Game mode achievements
                AchievementDefinition(
                    id = "sprint_complete",
                    name = "Sprinter",
                    description = "Complete a Sprint mode game (clear 40 lines)",
                    category = AchievementCategory.MODES,
                    iconResource = "ic_achievement_sprint",
                    progressRequired = 1,
                    points = 20
                ),
                AchievementDefinition(
                    id = "ultra_10k",
                    name = "Ultra Scorer",
                    description = "Score 10,000 points in Ultra mode (2 minute time limit)",
                    category = AchievementCategory.MODES,
                    iconResource = "ic_achievement_ultra",
                    progressRequired = 10000,
                    points = 25
                ),
                
                // Hidden/secret achievements
                AchievementDefinition(
                    id = "back_to_back",
                    name = "Back to Back",
                    description = "Perform 3 back-to-back Tetris clears",
                    category = AchievementCategory.SPECIAL,
                    iconResource = "ic_achievement_back_to_back",
                    progressRequired = 3,
                    points = 35,
                    isSecret = true
                ),
                AchievementDefinition(
                    id = "combo_7",
                    name = "Combo King",
                    description = "Achieve a 7+ combo",
                    category = AchievementCategory.SPECIAL,
                    iconResource = "ic_achievement_combo",
                    progressRequired = 7,
                    points = 40,
                    isSecret = true
                )
            )
        }
    }
}

/**
 * Categories for organizing achievements
 */
enum class AchievementCategory {
    GAMES,    // Number of games played
    SCORE,    // Points based achievements
    LINES,    // Line clearing achievements
    LEVEL,    // Level progression achievements
    SPECIAL,  // Special moves (Tetris, T-Spin, etc.)
    MODES     // Game mode specific achievements
}
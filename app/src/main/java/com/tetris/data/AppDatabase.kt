package com.tetris.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tetris.data.converters.DateConverter
import com.tetris.data.converters.BoardConverter
import com.tetris.data.converters.PieceConverter
import com.tetris.data.converters.IntArrayConverter
import com.tetris.data.converters.AchievementCategoryConverter
import com.tetris.data.dao.AchievementDao
import com.tetris.data.dao.GameStateDao
import com.tetris.data.dao.PlayerDao
import com.tetris.data.dao.ScoreDao
import com.tetris.data.dao.StatisticsDao
import com.tetris.data.dao.ThemeDao
import com.tetris.model.Achievement
import com.tetris.model.AchievementDefinition // Added import
import com.tetris.model.GameState
import com.tetris.model.HighScore
import com.tetris.model.Player
import com.tetris.model.Statistics
import com.tetris.model.Theme

/**
 * Main Room database class for the application.
 * Defines the database configuration and serves as the main access point for the persisted data.
 */
@Database(
    entities = [
        Player::class,
        GameState::class,
        HighScore::class,
        Statistics::class,
        Theme::class,
        Achievement::class,
        AchievementDefinition::class // Added entity
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    BoardConverter::class,
    PieceConverter::class,
    IntArrayConverter::class,
    AchievementCategoryConverter::class // Added converter
)
abstract class AppDatabase : RoomDatabase() {
    
    // DAOs
    abstract fun playerDao(): PlayerDao
    abstract fun gameStateDao(): GameStateDao
    abstract fun scoreDao(): ScoreDao
    abstract fun statisticsDao(): StatisticsDao
    abstract fun themeDao(): ThemeDao
    abstract fun achievementDao(): AchievementDao
    
    companion object {
        private const val DATABASE_NAME = "tetris_database"
        
        // Singleton instance
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Get the database instance - creates it if it doesn't exist
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        
        /**
         * Create the Room database
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration() // Recreate database if schema changes
            .build()
        }
    }
}
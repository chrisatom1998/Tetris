package com.tetris.data.generated

import androidx.room.RoomDatabase
import com.tetris.data.AppDatabase
import com.tetris.data.dao.*
import com.tetris.model.*

/**
 * This is a manually created implementation of Room's generated database code
 * to work around KAPT annotation processing issues
 */
class RoomDatabaseGeneratedImpl {
    
    companion object {
        // Create empty implementations of all DAOs
        fun createPlayerDao(db: AppDatabase): PlayerDao = EmptyPlayerDao()
        fun createGameStateDao(db: AppDatabase): GameStateDao = EmptyGameStateDao()
        fun createStatisticsDao(db: AppDatabase): StatisticsDao = EmptyStatisticsDao()
        fun createScoreDao(db: AppDatabase): ScoreDao = EmptyScoreDao() 
        fun createAchievementDao(db: AppDatabase): AchievementDao = EmptyAchievementDao()
        fun createThemeDao(db: AppDatabase): ThemeDao = EmptyThemeDao()
    }
}

// Empty implementations of each DAO
private class EmptyPlayerDao : PlayerDao {
    override suspend fun getPlayer(id: Long): Player? = null
    override suspend fun getAllPlayers(): List<Player> = emptyList()
    override suspend fun insertPlayer(player: Player): Long = 0
    override suspend fun updatePlayer(player: Player) {}
    override suspend fun deletePlayer(player: Player) {}
}

private class EmptyGameStateDao : GameStateDao {
    override suspend fun getGameState(id: Long): GameState? = null
    override suspend fun getAllGameStates(): List<GameState> = emptyList()
    override suspend fun insertGameState(gameState: GameState): Long = 0
    override suspend fun updateGameState(gameState: GameState) {}
    override suspend fun deleteGameState(gameState: GameState) {}
    override suspend fun getLatestGameState(): GameState? = null
}

private class EmptyStatisticsDao : StatisticsDao {
    override suspend fun getStatistics(id: Long): Statistics? = null
    override suspend fun getAllStatistics(): List<Statistics> = emptyList()
    override suspend fun insertStatistics(statistics: Statistics): Long = 0
    override suspend fun updateStatistics(statistics: Statistics) {}
    override suspend fun deleteStatistics(statistics: Statistics) {}
}

private class EmptyScoreDao : ScoreDao {
    override suspend fun getHighScores(limit: Int): List<HighScore> = emptyList()
    override suspend fun insertScore(score: HighScore): Long = 0
}

private class EmptyAchievementDao : AchievementDao {
    override suspend fun getAchievements(): List<Achievement> = emptyList()
    override suspend fun insertAchievement(achievement: Achievement): Long = 0
    override suspend fun updateAchievement(achievement: Achievement) {}
}

private class EmptyThemeDao : ThemeDao {
    override suspend fun getThemes(): List<Theme> = emptyList()
    override suspend fun insertTheme(theme: Theme): Long = 0
    override suspend fun updateTheme(theme: Theme) {}
    override suspend fun getActiveTheme(): Theme? = null
}

package com.tetris.data.generated

import androidx.room.RoomDatabase
import com.tetris.data.AppDatabase
import com.tetris.data.dao.*
import com.tetris.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

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
    override suspend fun insert(player: Player): Long = 0
    override suspend fun update(player: Player) {}
    override suspend fun delete(player: Player) {}
    override suspend fun getPlayerById(playerId: Int): Player? = null
    override suspend fun getPlayerByName(name: String): Player? = null
    override fun getAllPlayers(): Flow<List<Player>> = flowOf(emptyList())
    override fun getAllPlayersByScore(): Flow<List<Player>> = flowOf(emptyList())
    override suspend fun getLastActivePlayer(): Player? = null
    override suspend fun getTopPlayers(limit: Int): List<Player> = emptyList()
    override suspend fun updateHighScoreIfHigher(playerId: Int, score: Long) {}
    override suspend fun incrementGamesPlayed(playerId: Int) {}
    override suspend fun updatePlayerStats(playerId: Int, score: Long, lines: Int, time: Long) {}
    override suspend fun getPlayerCount(): Int = 0
}

private class EmptyGameStateDao : GameStateDao {
    override suspend fun insert(gameState: GameState): Long = 0
    override suspend fun update(gameState: GameState) {}
    override suspend fun delete(gameState: GameState) {}
    override suspend fun insertOrUpdate(gameState: GameState) {}
    override suspend fun getGameStateById(id: Int): GameState? = null
    override suspend fun getActiveGameStateForPlayer(playerId: Int): GameState? = null
    override fun getAllGameStatesForPlayer(playerId: Int): Flow<List<GameState>> = flowOf(emptyList())
    override suspend fun deleteByPlayerId(playerId: Int) {}
    override suspend fun deleteCompletedGamesForPlayer(playerId: Int) {}
    override suspend fun countActiveGamesForPlayer(playerId: Int): Int = 0
    override suspend fun markAllGamesAsOverForPlayer(playerId: Int) {}
    override suspend fun updateTimeElapsed(gameStateId: Int, timeElapsed: Long) {}
    override suspend fun cleanupOldGames() {}
}

private class EmptyStatisticsDao : StatisticsDao {
    override suspend fun insert(statistics: Statistics): Long = 0
    override suspend fun update(statistics: Statistics) {}
    override suspend fun delete(statistics: Statistics) {}
    override suspend fun getStatisticsForPlayer(playerId: Int): Statistics? = null
    override fun getAllStatistics(): Flow<List<Statistics>> = flowOf(emptyList())
    override suspend fun getTopStatistics(limit: Int): List<Statistics> = emptyList()
    override suspend fun getAverageStats(): StatisticsDao.AverageStats = StatisticsDao.AverageStats(0f,0f,0f,0f)
    override suspend fun incrementGamesPlayed(playerId: Int) {}
    override suspend fun incrementGamesWon(playerId: Int) {}
    override suspend fun updateHighScoreIfHigher(playerId: Int, score: Long) {}
    override suspend fun updateHighestLevelIfHigher(playerId: Int, level: Int) {}
    override suspend fun getLeaderboard(limit: Int): List<StatisticsDao.LeaderboardEntry> = emptyList()
    override suspend fun getStatisticsForAchievements(tetrisThreshold: Int, perfectClearThreshold: Int, comboThreshold: Int): List<Statistics> = emptyList()
    override suspend fun getPiecePlacementStats(playerId: Int): StatisticsDao.PiecePlacementStats? = null
    override suspend fun getMovementStats(playerId: Int): StatisticsDao.MovementStats? = null
    override suspend fun ensureStatisticsExistForPlayer(playerId: Int) {}
}

private class EmptyScoreDao : ScoreDao {
    override suspend fun insert(highScore: HighScore): Long = 0
    override suspend fun update(highScore: HighScore) {}
    override suspend fun delete(highScore: HighScore) {}
    override suspend fun getHighScoreById(id: Int): HighScore? = null
    override fun getHighScoresForPlayer(playerId: Int): Flow<List<HighScore>> = flowOf(emptyList())
    override suspend fun getTopScoresForGameMode(gameMode: String, limit: Int): List<HighScore> = emptyList()
    override suspend fun getTopScores(limit: Int): List<HighScore> = emptyList()
    override suspend fun isHighScore(playerId: Int, score: Long, maximumScores: Int): Boolean = false
    override suspend fun getDetailedHighScores(limit: Int): List<ScoreDao.DetailedHighScore> = emptyList()
    override suspend fun getPlayerBestScoreForMode(playerId: Int, gameMode: String): HighScore? = null
    override suspend fun getPlayerBestScore(playerId: Int): HighScore? = null
    override suspend fun recordHighScore(playerId: Int, score: Long, levelReached: Int, linesCleared: Int, gameMode: GameMode, maximumScores: Int) {}
    override suspend fun deleteExcessHighScores(playerId: Int, maxScores: Int) {}
    override suspend fun deleteOldHighScores() {}
}

private class EmptyAchievementDao : AchievementDao {
    override suspend fun insert(achievement: Achievement): Long = 0
    override suspend fun update(achievement: Achievement) {}
    override suspend fun delete(achievement: Achievement) {}
    override suspend fun getAchievementById(id: Int): Achievement? = null
    override suspend fun getPlayerAchievement(playerId: Int, achievementId: String): Achievement? = null
    override fun getPlayerAchievements(playerId: Int): Flow<List<Achievement>> = flowOf(emptyList())
    override fun getUnlockedAchievements(playerId: Int): Flow<List<Achievement>> = flowOf(emptyList())
    override fun getLockedAchievements(playerId: Int): Flow<List<Achievement>> = flowOf(emptyList())
    override suspend fun countUnlockedAchievements(playerId: Int): Int = 0
    override suspend fun unlockAchievement(playerId: Int, achievementId: String, date: Date): Int = 0
    override suspend fun hasAchievement(playerId: Int, achievementId: String): Boolean? = null
    override suspend fun getRecentAchievements(playerId: Int, limit: Int): List<Achievement> = emptyList()
    override fun getAchievementProgressData(playerId: Int): Flow<List<AchievementDao.AchievementProgress>> = flowOf(emptyList())
    override suspend fun ensurePlayerHasAchievements(playerId: Int, achievementIds: List<String>) {}
    override suspend fun trackAchievementProgress(playerId: Int, achievementId: String, currentProgress: Int, targetProgress: Int): Boolean = false
    override suspend fun checkAchievementsBasedOnStats(playerId: Int, totalGames: Int, highScore: Long, linesCleared: Int, level: Int, tetrisCount: Int): List<String> = emptyList()
}

private class EmptyThemeDao : ThemeDao {
    override suspend fun insert(theme: Theme): Long = 0
    override suspend fun update(theme: Theme) {}
    override suspend fun delete(theme: Theme) {}
    override suspend fun getThemeById(id: Int): Theme? = null
    override suspend fun getThemeByName(name: String): Theme? = null
    override fun getAllThemes(): Flow<List<Theme>> = flowOf(emptyList())
    override fun getUnlockedThemes(): Flow<List<Theme>> = flowOf(emptyList())
    override suspend fun getActiveTheme(): Theme? = null
    override suspend fun setActiveTheme(themeId: Int) {}
    override suspend fun deactivateAllThemes() {}
    override suspend fun activateTheme(themeId: Int) {}
    override suspend fun unlockTheme(themeId: Int) {}
    override suspend fun isThemeUnlocked(themeId: Int): Boolean = false
    override suspend fun getThemeCount(): Int = 0
}

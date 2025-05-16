package com.tetris.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.tetris.data.dao.AchievementDao;
import com.tetris.data.dao.AchievementDao_Impl;
import com.tetris.data.dao.GameStateDao;
import com.tetris.data.dao.GameStateDao_Impl;
import com.tetris.data.dao.PlayerDao;
import com.tetris.data.dao.PlayerDao_Impl;
import com.tetris.data.dao.ScoreDao;
import com.tetris.data.dao.ScoreDao_Impl;
import com.tetris.data.dao.StatisticsDao;
import com.tetris.data.dao.StatisticsDao_Impl;
import com.tetris.data.dao.ThemeDao;
import com.tetris.data.dao.ThemeDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PlayerDao _playerDao;

  private volatile GameStateDao _gameStateDao;

  private volatile ScoreDao _scoreDao;

  private volatile StatisticsDao _statisticsDao;

  private volatile ThemeDao _themeDao;

  private volatile AchievementDao _achievementDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `players` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `totalGames` INTEGER NOT NULL, `totalScore` INTEGER NOT NULL, `highScore` INTEGER NOT NULL, `totalLinesCleared` INTEGER NOT NULL, `maxLevel` INTEGER NOT NULL, `totalTimePlayed` INTEGER NOT NULL, `tetrisCount` INTEGER NOT NULL, `tspin` INTEGER NOT NULL, `perfectClearCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `game_states` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playerId` INTEGER NOT NULL, `board` TEXT NOT NULL, `currentPiece` TEXT, `nextPiece` TEXT, `heldPiece` TEXT, `hasHeld` INTEGER NOT NULL, `score` INTEGER NOT NULL, `level` INTEGER NOT NULL, `linesCleared` INTEGER NOT NULL, `combo` INTEGER NOT NULL, `backToBack` INTEGER NOT NULL, `singleLines` INTEGER NOT NULL, `doubleLines` INTEGER NOT NULL, `tripleLines` INTEGER NOT NULL, `tetrisCount` INTEGER NOT NULL, `tSpins` INTEGER NOT NULL, `tSpinSingles` INTEGER NOT NULL, `tSpinDoubles` INTEGER NOT NULL, `tSpinTriples` INTEGER NOT NULL, `perfectClearCount` INTEGER NOT NULL, `maxCombo` INTEGER NOT NULL, `iPiecesPlaced` INTEGER NOT NULL, `jPiecesPlaced` INTEGER NOT NULL, `lPiecesPlaced` INTEGER NOT NULL, `oPiecesPlaced` INTEGER NOT NULL, `sPiecesPlaced` INTEGER NOT NULL, `tPiecesPlaced` INTEGER NOT NULL, `zPiecesPlaced` INTEGER NOT NULL, `rotations` INTEGER NOT NULL, `movesLeft` INTEGER NOT NULL, `movesRight` INTEGER NOT NULL, `softDropCount` INTEGER NOT NULL, `hardDropCount` INTEGER NOT NULL, `holdPieceCount` INTEGER NOT NULL, `totalPiecesPlaced` INTEGER NOT NULL, `timeElapsed` INTEGER NOT NULL, `isGameOver` INTEGER NOT NULL, `isPaused` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `gameMode` TEXT NOT NULL, `randomSeed` INTEGER NOT NULL, FOREIGN KEY(`playerId`) REFERENCES `players`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `high_scores` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playerId` INTEGER NOT NULL, `score` INTEGER NOT NULL, `levelReached` INTEGER NOT NULL, `linesCleared` INTEGER NOT NULL, `achievedAt` INTEGER NOT NULL, `gameMode` TEXT NOT NULL, `tetrisCount` INTEGER NOT NULL, `maxCombo` INTEGER NOT NULL, `perfectClearCount` INTEGER NOT NULL, `gameDuration` INTEGER NOT NULL, `deviceModel` TEXT NOT NULL, `appVersion` TEXT NOT NULL, FOREIGN KEY(`playerId`) REFERENCES `players`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `statistics` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playerId` INTEGER NOT NULL, `totalGames` INTEGER NOT NULL, `gamesWon` INTEGER NOT NULL, `totalScore` INTEGER NOT NULL, `highestScore` INTEGER NOT NULL, `totalLinesCleared` INTEGER NOT NULL, `highestLinesInGame` INTEGER NOT NULL, `totalPiecesPlaced` INTEGER NOT NULL, `highestLevel` INTEGER NOT NULL, `totalTimePlayed` INTEGER NOT NULL, `longestGame` INTEGER NOT NULL, `fastestClearTime` INTEGER, `singleLines` INTEGER NOT NULL, `doubleLines` INTEGER NOT NULL, `tripleLines` INTEGER NOT NULL, `tetrisCount` INTEGER NOT NULL, `tSpins` INTEGER NOT NULL, `tSpinSingles` INTEGER NOT NULL, `tSpinDoubles` INTEGER NOT NULL, `tSpinTriples` INTEGER NOT NULL, `perfectClearCount` INTEGER NOT NULL, `comboMax` INTEGER NOT NULL, `backToBackCount` INTEGER NOT NULL, `iPiecesPlaced` INTEGER NOT NULL, `jPiecesPlaced` INTEGER NOT NULL, `lPiecesPlaced` INTEGER NOT NULL, `oPiecesPlaced` INTEGER NOT NULL, `sPiecesPlaced` INTEGER NOT NULL, `tPiecesPlaced` INTEGER NOT NULL, `zPiecesPlaced` INTEGER NOT NULL, `lastPlayed` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `achievement` INTEGER NOT NULL, `rotations` INTEGER NOT NULL, `movesLeft` INTEGER NOT NULL, `movesRight` INTEGER NOT NULL, `softDropCount` INTEGER NOT NULL, `hardDropCount` INTEGER NOT NULL, `holdPieceCount` INTEGER NOT NULL, FOREIGN KEY(`playerId`) REFERENCES `players`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `themes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `backgroundColor` INTEGER NOT NULL, `textColor` INTEGER NOT NULL, `gridColor` INTEGER NOT NULL, `highlightColor` INTEGER NOT NULL, `blockStyle` TEXT NOT NULL, `backgroundImageRes` TEXT, `pieceColors` TEXT NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockCriteria` TEXT NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playerId` INTEGER NOT NULL, `achievementId` TEXT NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedAt` INTEGER, FOREIGN KEY(`playerId`) REFERENCES `players`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievement_definitions` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `iconResource` TEXT NOT NULL, `progressRequired` INTEGER NOT NULL, `isSecret` INTEGER NOT NULL, `points` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bd7c1d32bcde310707bb62acb7830413')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `players`");
        db.execSQL("DROP TABLE IF EXISTS `game_states`");
        db.execSQL("DROP TABLE IF EXISTS `high_scores`");
        db.execSQL("DROP TABLE IF EXISTS `statistics`");
        db.execSQL("DROP TABLE IF EXISTS `themes`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        db.execSQL("DROP TABLE IF EXISTS `achievement_definitions`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPlayers = new HashMap<String, TableInfo.Column>(12);
        _columnsPlayers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("totalGames", new TableInfo.Column("totalGames", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("totalScore", new TableInfo.Column("totalScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("highScore", new TableInfo.Column("highScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("totalLinesCleared", new TableInfo.Column("totalLinesCleared", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("maxLevel", new TableInfo.Column("maxLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("totalTimePlayed", new TableInfo.Column("totalTimePlayed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("tetrisCount", new TableInfo.Column("tetrisCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("tspin", new TableInfo.Column("tspin", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("perfectClearCount", new TableInfo.Column("perfectClearCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlayers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlayers = new TableInfo("players", _columnsPlayers, _foreignKeysPlayers, _indicesPlayers);
        final TableInfo _existingPlayers = TableInfo.read(db, "players");
        if (!_infoPlayers.equals(_existingPlayers)) {
          return new RoomOpenHelper.ValidationResult(false, "players(com.tetris.model.Player).\n"
                  + " Expected:\n" + _infoPlayers + "\n"
                  + " Found:\n" + _existingPlayers);
        }
        final HashMap<String, TableInfo.Column> _columnsGameStates = new HashMap<String, TableInfo.Column>(42);
        _columnsGameStates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("playerId", new TableInfo.Column("playerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("board", new TableInfo.Column("board", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("currentPiece", new TableInfo.Column("currentPiece", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("nextPiece", new TableInfo.Column("nextPiece", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("heldPiece", new TableInfo.Column("heldPiece", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("hasHeld", new TableInfo.Column("hasHeld", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("linesCleared", new TableInfo.Column("linesCleared", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("combo", new TableInfo.Column("combo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("backToBack", new TableInfo.Column("backToBack", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("singleLines", new TableInfo.Column("singleLines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("doubleLines", new TableInfo.Column("doubleLines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tripleLines", new TableInfo.Column("tripleLines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tetrisCount", new TableInfo.Column("tetrisCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tSpins", new TableInfo.Column("tSpins", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tSpinSingles", new TableInfo.Column("tSpinSingles", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tSpinDoubles", new TableInfo.Column("tSpinDoubles", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tSpinTriples", new TableInfo.Column("tSpinTriples", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("perfectClearCount", new TableInfo.Column("perfectClearCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("maxCombo", new TableInfo.Column("maxCombo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("iPiecesPlaced", new TableInfo.Column("iPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("jPiecesPlaced", new TableInfo.Column("jPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("lPiecesPlaced", new TableInfo.Column("lPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("oPiecesPlaced", new TableInfo.Column("oPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("sPiecesPlaced", new TableInfo.Column("sPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("tPiecesPlaced", new TableInfo.Column("tPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("zPiecesPlaced", new TableInfo.Column("zPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("rotations", new TableInfo.Column("rotations", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("movesLeft", new TableInfo.Column("movesLeft", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("movesRight", new TableInfo.Column("movesRight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("softDropCount", new TableInfo.Column("softDropCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("hardDropCount", new TableInfo.Column("hardDropCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("holdPieceCount", new TableInfo.Column("holdPieceCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("totalPiecesPlaced", new TableInfo.Column("totalPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("timeElapsed", new TableInfo.Column("timeElapsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("isGameOver", new TableInfo.Column("isGameOver", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("isPaused", new TableInfo.Column("isPaused", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("gameMode", new TableInfo.Column("gameMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameStates.put("randomSeed", new TableInfo.Column("randomSeed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGameStates = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysGameStates.add(new TableInfo.ForeignKey("players", "CASCADE", "NO ACTION", Arrays.asList("playerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGameStates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGameStates = new TableInfo("game_states", _columnsGameStates, _foreignKeysGameStates, _indicesGameStates);
        final TableInfo _existingGameStates = TableInfo.read(db, "game_states");
        if (!_infoGameStates.equals(_existingGameStates)) {
          return new RoomOpenHelper.ValidationResult(false, "game_states(com.tetris.model.GameState).\n"
                  + " Expected:\n" + _infoGameStates + "\n"
                  + " Found:\n" + _existingGameStates);
        }
        final HashMap<String, TableInfo.Column> _columnsHighScores = new HashMap<String, TableInfo.Column>(13);
        _columnsHighScores.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("playerId", new TableInfo.Column("playerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("levelReached", new TableInfo.Column("levelReached", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("linesCleared", new TableInfo.Column("linesCleared", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("achievedAt", new TableInfo.Column("achievedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("gameMode", new TableInfo.Column("gameMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("tetrisCount", new TableInfo.Column("tetrisCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("maxCombo", new TableInfo.Column("maxCombo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("perfectClearCount", new TableInfo.Column("perfectClearCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("gameDuration", new TableInfo.Column("gameDuration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("deviceModel", new TableInfo.Column("deviceModel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighScores.put("appVersion", new TableInfo.Column("appVersion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHighScores = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysHighScores.add(new TableInfo.ForeignKey("players", "CASCADE", "NO ACTION", Arrays.asList("playerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesHighScores = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHighScores = new TableInfo("high_scores", _columnsHighScores, _foreignKeysHighScores, _indicesHighScores);
        final TableInfo _existingHighScores = TableInfo.read(db, "high_scores");
        if (!_infoHighScores.equals(_existingHighScores)) {
          return new RoomOpenHelper.ValidationResult(false, "high_scores(com.tetris.model.HighScore).\n"
                  + " Expected:\n" + _infoHighScores + "\n"
                  + " Found:\n" + _existingHighScores);
        }
        final HashMap<String, TableInfo.Column> _columnsStatistics = new HashMap<String, TableInfo.Column>(41);
        _columnsStatistics.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("playerId", new TableInfo.Column("playerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalGames", new TableInfo.Column("totalGames", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("gamesWon", new TableInfo.Column("gamesWon", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalScore", new TableInfo.Column("totalScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("highestScore", new TableInfo.Column("highestScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalLinesCleared", new TableInfo.Column("totalLinesCleared", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("highestLinesInGame", new TableInfo.Column("highestLinesInGame", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalPiecesPlaced", new TableInfo.Column("totalPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("highestLevel", new TableInfo.Column("highestLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("totalTimePlayed", new TableInfo.Column("totalTimePlayed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("longestGame", new TableInfo.Column("longestGame", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("fastestClearTime", new TableInfo.Column("fastestClearTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("singleLines", new TableInfo.Column("singleLines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("doubleLines", new TableInfo.Column("doubleLines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tripleLines", new TableInfo.Column("tripleLines", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tetrisCount", new TableInfo.Column("tetrisCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tSpins", new TableInfo.Column("tSpins", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tSpinSingles", new TableInfo.Column("tSpinSingles", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tSpinDoubles", new TableInfo.Column("tSpinDoubles", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tSpinTriples", new TableInfo.Column("tSpinTriples", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("perfectClearCount", new TableInfo.Column("perfectClearCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("comboMax", new TableInfo.Column("comboMax", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("backToBackCount", new TableInfo.Column("backToBackCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("iPiecesPlaced", new TableInfo.Column("iPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("jPiecesPlaced", new TableInfo.Column("jPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("lPiecesPlaced", new TableInfo.Column("lPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("oPiecesPlaced", new TableInfo.Column("oPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("sPiecesPlaced", new TableInfo.Column("sPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("tPiecesPlaced", new TableInfo.Column("tPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("zPiecesPlaced", new TableInfo.Column("zPiecesPlaced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("lastPlayed", new TableInfo.Column("lastPlayed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("longestStreak", new TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("achievement", new TableInfo.Column("achievement", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("rotations", new TableInfo.Column("rotations", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("movesLeft", new TableInfo.Column("movesLeft", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("movesRight", new TableInfo.Column("movesRight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("softDropCount", new TableInfo.Column("softDropCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("hardDropCount", new TableInfo.Column("hardDropCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatistics.put("holdPieceCount", new TableInfo.Column("holdPieceCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStatistics = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysStatistics.add(new TableInfo.ForeignKey("players", "CASCADE", "NO ACTION", Arrays.asList("playerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesStatistics = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStatistics = new TableInfo("statistics", _columnsStatistics, _foreignKeysStatistics, _indicesStatistics);
        final TableInfo _existingStatistics = TableInfo.read(db, "statistics");
        if (!_infoStatistics.equals(_existingStatistics)) {
          return new RoomOpenHelper.ValidationResult(false, "statistics(com.tetris.model.Statistics).\n"
                  + " Expected:\n" + _infoStatistics + "\n"
                  + " Found:\n" + _existingStatistics);
        }
        final HashMap<String, TableInfo.Column> _columnsThemes = new HashMap<String, TableInfo.Column>(13);
        _columnsThemes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("backgroundColor", new TableInfo.Column("backgroundColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("textColor", new TableInfo.Column("textColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("gridColor", new TableInfo.Column("gridColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("highlightColor", new TableInfo.Column("highlightColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("blockStyle", new TableInfo.Column("blockStyle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("backgroundImageRes", new TableInfo.Column("backgroundImageRes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("pieceColors", new TableInfo.Column("pieceColors", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("unlockCriteria", new TableInfo.Column("unlockCriteria", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThemes.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysThemes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesThemes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoThemes = new TableInfo("themes", _columnsThemes, _foreignKeysThemes, _indicesThemes);
        final TableInfo _existingThemes = TableInfo.read(db, "themes");
        if (!_infoThemes.equals(_existingThemes)) {
          return new RoomOpenHelper.ValidationResult(false, "themes(com.tetris.model.Theme).\n"
                  + " Expected:\n" + _infoThemes + "\n"
                  + " Found:\n" + _existingThemes);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(5);
        _columnsAchievements.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("playerId", new TableInfo.Column("playerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("achievementId", new TableInfo.Column("achievementId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAchievements.add(new TableInfo.ForeignKey("players", "CASCADE", "NO ACTION", Arrays.asList("playerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.tetris.model.Achievement).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievementDefinitions = new HashMap<String, TableInfo.Column>(8);
        _columnsAchievementDefinitions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("iconResource", new TableInfo.Column("iconResource", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("progressRequired", new TableInfo.Column("progressRequired", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("isSecret", new TableInfo.Column("isSecret", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievementDefinitions.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievementDefinitions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievementDefinitions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievementDefinitions = new TableInfo("achievement_definitions", _columnsAchievementDefinitions, _foreignKeysAchievementDefinitions, _indicesAchievementDefinitions);
        final TableInfo _existingAchievementDefinitions = TableInfo.read(db, "achievement_definitions");
        if (!_infoAchievementDefinitions.equals(_existingAchievementDefinitions)) {
          return new RoomOpenHelper.ValidationResult(false, "achievement_definitions(com.tetris.model.AchievementDefinition).\n"
                  + " Expected:\n" + _infoAchievementDefinitions + "\n"
                  + " Found:\n" + _existingAchievementDefinitions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "bd7c1d32bcde310707bb62acb7830413", "169af6edbd7e8590b76df5ef46621487");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "players","game_states","high_scores","statistics","themes","achievements","achievement_definitions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `players`");
      _db.execSQL("DELETE FROM `game_states`");
      _db.execSQL("DELETE FROM `high_scores`");
      _db.execSQL("DELETE FROM `statistics`");
      _db.execSQL("DELETE FROM `themes`");
      _db.execSQL("DELETE FROM `achievements`");
      _db.execSQL("DELETE FROM `achievement_definitions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PlayerDao.class, PlayerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GameStateDao.class, GameStateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ScoreDao.class, ScoreDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StatisticsDao.class, StatisticsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ThemeDao.class, ThemeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PlayerDao playerDao() {
    if (_playerDao != null) {
      return _playerDao;
    } else {
      synchronized(this) {
        if(_playerDao == null) {
          _playerDao = new PlayerDao_Impl(this);
        }
        return _playerDao;
      }
    }
  }

  @Override
  public GameStateDao gameStateDao() {
    if (_gameStateDao != null) {
      return _gameStateDao;
    } else {
      synchronized(this) {
        if(_gameStateDao == null) {
          _gameStateDao = new GameStateDao_Impl(this);
        }
        return _gameStateDao;
      }
    }
  }

  @Override
  public ScoreDao scoreDao() {
    if (_scoreDao != null) {
      return _scoreDao;
    } else {
      synchronized(this) {
        if(_scoreDao == null) {
          _scoreDao = new ScoreDao_Impl(this);
        }
        return _scoreDao;
      }
    }
  }

  @Override
  public StatisticsDao statisticsDao() {
    if (_statisticsDao != null) {
      return _statisticsDao;
    } else {
      synchronized(this) {
        if(_statisticsDao == null) {
          _statisticsDao = new StatisticsDao_Impl(this);
        }
        return _statisticsDao;
      }
    }
  }

  @Override
  public ThemeDao themeDao() {
    if (_themeDao != null) {
      return _themeDao;
    } else {
      synchronized(this) {
        if(_themeDao == null) {
          _themeDao = new ThemeDao_Impl(this);
        }
        return _themeDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }
}

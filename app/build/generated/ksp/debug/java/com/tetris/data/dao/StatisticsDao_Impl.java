package com.tetris.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.data.converters.DateConverter;
import com.tetris.model.Statistics;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StatisticsDao_Impl implements StatisticsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Statistics> __insertionAdapterOfStatistics;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<Statistics> __deletionAdapterOfStatistics;

  private final EntityDeletionOrUpdateAdapter<Statistics> __updateAdapterOfStatistics;

  private final SharedSQLiteStatement __preparedStmtOfIncrementGamesPlayed;

  private final SharedSQLiteStatement __preparedStmtOfIncrementGamesWon;

  private final SharedSQLiteStatement __preparedStmtOfUpdateHighScoreIfHigher;

  private final SharedSQLiteStatement __preparedStmtOfUpdateHighestLevelIfHigher;

  public StatisticsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStatistics = new EntityInsertionAdapter<Statistics>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `statistics` (`id`,`playerId`,`totalGames`,`gamesWon`,`totalScore`,`highestScore`,`totalLinesCleared`,`highestLinesInGame`,`totalPiecesPlaced`,`highestLevel`,`totalTimePlayed`,`longestGame`,`fastestClearTime`,`singleLines`,`doubleLines`,`tripleLines`,`tetrisCount`,`tSpins`,`tSpinSingles`,`tSpinDoubles`,`tSpinTriples`,`perfectClearCount`,`comboMax`,`backToBackCount`,`iPiecesPlaced`,`jPiecesPlaced`,`lPiecesPlaced`,`oPiecesPlaced`,`sPiecesPlaced`,`tPiecesPlaced`,`zPiecesPlaced`,`lastPlayed`,`currentStreak`,`longestStreak`,`achievement`,`rotations`,`movesLeft`,`movesRight`,`softDropCount`,`hardDropCount`,`holdPieceCount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Statistics entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        statement.bindLong(3, entity.getTotalGames());
        statement.bindLong(4, entity.getGamesWon());
        statement.bindLong(5, entity.getTotalScore());
        statement.bindLong(6, entity.getHighestScore());
        statement.bindLong(7, entity.getTotalLinesCleared());
        statement.bindLong(8, entity.getHighestLinesInGame());
        statement.bindLong(9, entity.getTotalPiecesPlaced());
        statement.bindLong(10, entity.getHighestLevel());
        statement.bindLong(11, entity.getTotalTimePlayed());
        statement.bindLong(12, entity.getLongestGame());
        if (entity.getFastestClearTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getFastestClearTime());
        }
        statement.bindLong(14, entity.getSingleLines());
        statement.bindLong(15, entity.getDoubleLines());
        statement.bindLong(16, entity.getTripleLines());
        statement.bindLong(17, entity.getTetrisCount());
        statement.bindLong(18, entity.getTSpins());
        statement.bindLong(19, entity.getTSpinSingles());
        statement.bindLong(20, entity.getTSpinDoubles());
        statement.bindLong(21, entity.getTSpinTriples());
        statement.bindLong(22, entity.getPerfectClearCount());
        statement.bindLong(23, entity.getComboMax());
        statement.bindLong(24, entity.getBackToBackCount());
        statement.bindLong(25, entity.getIPiecesPlaced());
        statement.bindLong(26, entity.getJPiecesPlaced());
        statement.bindLong(27, entity.getLPiecesPlaced());
        statement.bindLong(28, entity.getOPiecesPlaced());
        statement.bindLong(29, entity.getSPiecesPlaced());
        statement.bindLong(30, entity.getTPiecesPlaced());
        statement.bindLong(31, entity.getZPiecesPlaced());
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getLastPlayed());
        if (_tmp == null) {
          statement.bindNull(32);
        } else {
          statement.bindLong(32, _tmp);
        }
        statement.bindLong(33, entity.getCurrentStreak());
        statement.bindLong(34, entity.getLongestStreak());
        statement.bindLong(35, entity.getAchievement());
        statement.bindLong(36, entity.getRotations());
        statement.bindLong(37, entity.getMovesLeft());
        statement.bindLong(38, entity.getMovesRight());
        statement.bindLong(39, entity.getSoftDropCount());
        statement.bindLong(40, entity.getHardDropCount());
        statement.bindLong(41, entity.getHoldPieceCount());
      }
    };
    this.__deletionAdapterOfStatistics = new EntityDeletionOrUpdateAdapter<Statistics>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `statistics` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Statistics entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStatistics = new EntityDeletionOrUpdateAdapter<Statistics>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `statistics` SET `id` = ?,`playerId` = ?,`totalGames` = ?,`gamesWon` = ?,`totalScore` = ?,`highestScore` = ?,`totalLinesCleared` = ?,`highestLinesInGame` = ?,`totalPiecesPlaced` = ?,`highestLevel` = ?,`totalTimePlayed` = ?,`longestGame` = ?,`fastestClearTime` = ?,`singleLines` = ?,`doubleLines` = ?,`tripleLines` = ?,`tetrisCount` = ?,`tSpins` = ?,`tSpinSingles` = ?,`tSpinDoubles` = ?,`tSpinTriples` = ?,`perfectClearCount` = ?,`comboMax` = ?,`backToBackCount` = ?,`iPiecesPlaced` = ?,`jPiecesPlaced` = ?,`lPiecesPlaced` = ?,`oPiecesPlaced` = ?,`sPiecesPlaced` = ?,`tPiecesPlaced` = ?,`zPiecesPlaced` = ?,`lastPlayed` = ?,`currentStreak` = ?,`longestStreak` = ?,`achievement` = ?,`rotations` = ?,`movesLeft` = ?,`movesRight` = ?,`softDropCount` = ?,`hardDropCount` = ?,`holdPieceCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Statistics entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        statement.bindLong(3, entity.getTotalGames());
        statement.bindLong(4, entity.getGamesWon());
        statement.bindLong(5, entity.getTotalScore());
        statement.bindLong(6, entity.getHighestScore());
        statement.bindLong(7, entity.getTotalLinesCleared());
        statement.bindLong(8, entity.getHighestLinesInGame());
        statement.bindLong(9, entity.getTotalPiecesPlaced());
        statement.bindLong(10, entity.getHighestLevel());
        statement.bindLong(11, entity.getTotalTimePlayed());
        statement.bindLong(12, entity.getLongestGame());
        if (entity.getFastestClearTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getFastestClearTime());
        }
        statement.bindLong(14, entity.getSingleLines());
        statement.bindLong(15, entity.getDoubleLines());
        statement.bindLong(16, entity.getTripleLines());
        statement.bindLong(17, entity.getTetrisCount());
        statement.bindLong(18, entity.getTSpins());
        statement.bindLong(19, entity.getTSpinSingles());
        statement.bindLong(20, entity.getTSpinDoubles());
        statement.bindLong(21, entity.getTSpinTriples());
        statement.bindLong(22, entity.getPerfectClearCount());
        statement.bindLong(23, entity.getComboMax());
        statement.bindLong(24, entity.getBackToBackCount());
        statement.bindLong(25, entity.getIPiecesPlaced());
        statement.bindLong(26, entity.getJPiecesPlaced());
        statement.bindLong(27, entity.getLPiecesPlaced());
        statement.bindLong(28, entity.getOPiecesPlaced());
        statement.bindLong(29, entity.getSPiecesPlaced());
        statement.bindLong(30, entity.getTPiecesPlaced());
        statement.bindLong(31, entity.getZPiecesPlaced());
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getLastPlayed());
        if (_tmp == null) {
          statement.bindNull(32);
        } else {
          statement.bindLong(32, _tmp);
        }
        statement.bindLong(33, entity.getCurrentStreak());
        statement.bindLong(34, entity.getLongestStreak());
        statement.bindLong(35, entity.getAchievement());
        statement.bindLong(36, entity.getRotations());
        statement.bindLong(37, entity.getMovesLeft());
        statement.bindLong(38, entity.getMovesRight());
        statement.bindLong(39, entity.getSoftDropCount());
        statement.bindLong(40, entity.getHardDropCount());
        statement.bindLong(41, entity.getHoldPieceCount());
        statement.bindLong(42, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementGamesPlayed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET totalGames = totalGames + 1 WHERE playerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementGamesWon = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET gamesWon = gamesWon + 1 WHERE playerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateHighScoreIfHigher = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET highestScore = ? WHERE playerId = ? AND highestScore < ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateHighestLevelIfHigher = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE statistics SET highestLevel = ? WHERE playerId = ? AND highestLevel < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Statistics statistics, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStatistics.insertAndReturnId(statistics);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Statistics statistics, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStatistics.handle(statistics);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Statistics statistics, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStatistics.handle(statistics);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object ensureStatisticsExistForPlayer(final int playerId,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> StatisticsDao.DefaultImpls.ensureStatisticsExistForPlayer(StatisticsDao_Impl.this, playerId, __cont), $completion);
  }

  @Override
  public Object incrementGamesPlayed(final int playerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementGamesPlayed.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementGamesPlayed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementGamesWon(final int playerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementGamesWon.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playerId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementGamesWon.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHighScoreIfHigher(final int playerId, final long score,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateHighScoreIfHigher.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, playerId);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, score);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateHighScoreIfHigher.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHighestLevelIfHigher(final int playerId, final int level,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateHighestLevelIfHigher.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, level);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, playerId);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, level);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateHighestLevelIfHigher.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getStatisticsForPlayer(final int playerId,
      final Continuation<? super Statistics> $completion) {
    final String _sql = "SELECT * FROM statistics WHERE playerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Statistics>() {
      @Override
      @Nullable
      public Statistics call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfGamesWon = CursorUtil.getColumnIndexOrThrow(_cursor, "gamesWon");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highestScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfHighestLinesInGame = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLinesInGame");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfLongestGame = CursorUtil.getColumnIndexOrThrow(_cursor, "longestGame");
          final int _cursorIndexOfFastestClearTime = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestClearTime");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfComboMax = CursorUtil.getColumnIndexOrThrow(_cursor, "comboMax");
          final int _cursorIndexOfBackToBackCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBackCount");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfLastPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayed");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "achievement");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final Statistics _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final int _tmpGamesWon;
            _tmpGamesWon = _cursor.getInt(_cursorIndexOfGamesWon);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighestScore;
            _tmpHighestScore = _cursor.getLong(_cursorIndexOfHighestScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpHighestLinesInGame;
            _tmpHighestLinesInGame = _cursor.getInt(_cursorIndexOfHighestLinesInGame);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final long _tmpLongestGame;
            _tmpLongestGame = _cursor.getLong(_cursorIndexOfLongestGame);
            final Long _tmpFastestClearTime;
            if (_cursor.isNull(_cursorIndexOfFastestClearTime)) {
              _tmpFastestClearTime = null;
            } else {
              _tmpFastestClearTime = _cursor.getLong(_cursorIndexOfFastestClearTime);
            }
            final int _tmpSingleLines;
            _tmpSingleLines = _cursor.getInt(_cursorIndexOfSingleLines);
            final int _tmpDoubleLines;
            _tmpDoubleLines = _cursor.getInt(_cursorIndexOfDoubleLines);
            final int _tmpTripleLines;
            _tmpTripleLines = _cursor.getInt(_cursorIndexOfTripleLines);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTSpins;
            _tmpTSpins = _cursor.getInt(_cursorIndexOfTSpins);
            final int _tmpTSpinSingles;
            _tmpTSpinSingles = _cursor.getInt(_cursorIndexOfTSpinSingles);
            final int _tmpTSpinDoubles;
            _tmpTSpinDoubles = _cursor.getInt(_cursorIndexOfTSpinDoubles);
            final int _tmpTSpinTriples;
            _tmpTSpinTriples = _cursor.getInt(_cursorIndexOfTSpinTriples);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final int _tmpComboMax;
            _tmpComboMax = _cursor.getInt(_cursorIndexOfComboMax);
            final int _tmpBackToBackCount;
            _tmpBackToBackCount = _cursor.getInt(_cursorIndexOfBackToBackCount);
            final int _tmpIPiecesPlaced;
            _tmpIPiecesPlaced = _cursor.getInt(_cursorIndexOfIPiecesPlaced);
            final int _tmpJPiecesPlaced;
            _tmpJPiecesPlaced = _cursor.getInt(_cursorIndexOfJPiecesPlaced);
            final int _tmpLPiecesPlaced;
            _tmpLPiecesPlaced = _cursor.getInt(_cursorIndexOfLPiecesPlaced);
            final int _tmpOPiecesPlaced;
            _tmpOPiecesPlaced = _cursor.getInt(_cursorIndexOfOPiecesPlaced);
            final int _tmpSPiecesPlaced;
            _tmpSPiecesPlaced = _cursor.getInt(_cursorIndexOfSPiecesPlaced);
            final int _tmpTPiecesPlaced;
            _tmpTPiecesPlaced = _cursor.getInt(_cursorIndexOfTPiecesPlaced);
            final int _tmpZPiecesPlaced;
            _tmpZPiecesPlaced = _cursor.getInt(_cursorIndexOfZPiecesPlaced);
            final Date _tmpLastPlayed;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLastPlayed)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLastPlayed);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastPlayed = _tmp_1;
            }
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final int _tmpAchievement;
            _tmpAchievement = _cursor.getInt(_cursorIndexOfAchievement);
            final int _tmpRotations;
            _tmpRotations = _cursor.getInt(_cursorIndexOfRotations);
            final int _tmpMovesLeft;
            _tmpMovesLeft = _cursor.getInt(_cursorIndexOfMovesLeft);
            final int _tmpMovesRight;
            _tmpMovesRight = _cursor.getInt(_cursorIndexOfMovesRight);
            final int _tmpSoftDropCount;
            _tmpSoftDropCount = _cursor.getInt(_cursorIndexOfSoftDropCount);
            final int _tmpHardDropCount;
            _tmpHardDropCount = _cursor.getInt(_cursorIndexOfHardDropCount);
            final int _tmpHoldPieceCount;
            _tmpHoldPieceCount = _cursor.getInt(_cursorIndexOfHoldPieceCount);
            _result = new Statistics(_tmpId,_tmpPlayerId,_tmpTotalGames,_tmpGamesWon,_tmpTotalScore,_tmpHighestScore,_tmpTotalLinesCleared,_tmpHighestLinesInGame,_tmpTotalPiecesPlaced,_tmpHighestLevel,_tmpTotalTimePlayed,_tmpLongestGame,_tmpFastestClearTime,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpComboMax,_tmpBackToBackCount,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpLastPlayed,_tmpCurrentStreak,_tmpLongestStreak,_tmpAchievement,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Statistics>> getAllStatistics() {
    final String _sql = "SELECT * FROM statistics ORDER BY highestScore DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"statistics"}, new Callable<List<Statistics>>() {
      @Override
      @NonNull
      public List<Statistics> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfGamesWon = CursorUtil.getColumnIndexOrThrow(_cursor, "gamesWon");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highestScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfHighestLinesInGame = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLinesInGame");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfLongestGame = CursorUtil.getColumnIndexOrThrow(_cursor, "longestGame");
          final int _cursorIndexOfFastestClearTime = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestClearTime");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfComboMax = CursorUtil.getColumnIndexOrThrow(_cursor, "comboMax");
          final int _cursorIndexOfBackToBackCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBackCount");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfLastPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayed");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "achievement");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final List<Statistics> _result = new ArrayList<Statistics>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Statistics _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final int _tmpGamesWon;
            _tmpGamesWon = _cursor.getInt(_cursorIndexOfGamesWon);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighestScore;
            _tmpHighestScore = _cursor.getLong(_cursorIndexOfHighestScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpHighestLinesInGame;
            _tmpHighestLinesInGame = _cursor.getInt(_cursorIndexOfHighestLinesInGame);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final long _tmpLongestGame;
            _tmpLongestGame = _cursor.getLong(_cursorIndexOfLongestGame);
            final Long _tmpFastestClearTime;
            if (_cursor.isNull(_cursorIndexOfFastestClearTime)) {
              _tmpFastestClearTime = null;
            } else {
              _tmpFastestClearTime = _cursor.getLong(_cursorIndexOfFastestClearTime);
            }
            final int _tmpSingleLines;
            _tmpSingleLines = _cursor.getInt(_cursorIndexOfSingleLines);
            final int _tmpDoubleLines;
            _tmpDoubleLines = _cursor.getInt(_cursorIndexOfDoubleLines);
            final int _tmpTripleLines;
            _tmpTripleLines = _cursor.getInt(_cursorIndexOfTripleLines);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTSpins;
            _tmpTSpins = _cursor.getInt(_cursorIndexOfTSpins);
            final int _tmpTSpinSingles;
            _tmpTSpinSingles = _cursor.getInt(_cursorIndexOfTSpinSingles);
            final int _tmpTSpinDoubles;
            _tmpTSpinDoubles = _cursor.getInt(_cursorIndexOfTSpinDoubles);
            final int _tmpTSpinTriples;
            _tmpTSpinTriples = _cursor.getInt(_cursorIndexOfTSpinTriples);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final int _tmpComboMax;
            _tmpComboMax = _cursor.getInt(_cursorIndexOfComboMax);
            final int _tmpBackToBackCount;
            _tmpBackToBackCount = _cursor.getInt(_cursorIndexOfBackToBackCount);
            final int _tmpIPiecesPlaced;
            _tmpIPiecesPlaced = _cursor.getInt(_cursorIndexOfIPiecesPlaced);
            final int _tmpJPiecesPlaced;
            _tmpJPiecesPlaced = _cursor.getInt(_cursorIndexOfJPiecesPlaced);
            final int _tmpLPiecesPlaced;
            _tmpLPiecesPlaced = _cursor.getInt(_cursorIndexOfLPiecesPlaced);
            final int _tmpOPiecesPlaced;
            _tmpOPiecesPlaced = _cursor.getInt(_cursorIndexOfOPiecesPlaced);
            final int _tmpSPiecesPlaced;
            _tmpSPiecesPlaced = _cursor.getInt(_cursorIndexOfSPiecesPlaced);
            final int _tmpTPiecesPlaced;
            _tmpTPiecesPlaced = _cursor.getInt(_cursorIndexOfTPiecesPlaced);
            final int _tmpZPiecesPlaced;
            _tmpZPiecesPlaced = _cursor.getInt(_cursorIndexOfZPiecesPlaced);
            final Date _tmpLastPlayed;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLastPlayed)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLastPlayed);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastPlayed = _tmp_1;
            }
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final int _tmpAchievement;
            _tmpAchievement = _cursor.getInt(_cursorIndexOfAchievement);
            final int _tmpRotations;
            _tmpRotations = _cursor.getInt(_cursorIndexOfRotations);
            final int _tmpMovesLeft;
            _tmpMovesLeft = _cursor.getInt(_cursorIndexOfMovesLeft);
            final int _tmpMovesRight;
            _tmpMovesRight = _cursor.getInt(_cursorIndexOfMovesRight);
            final int _tmpSoftDropCount;
            _tmpSoftDropCount = _cursor.getInt(_cursorIndexOfSoftDropCount);
            final int _tmpHardDropCount;
            _tmpHardDropCount = _cursor.getInt(_cursorIndexOfHardDropCount);
            final int _tmpHoldPieceCount;
            _tmpHoldPieceCount = _cursor.getInt(_cursorIndexOfHoldPieceCount);
            _item = new Statistics(_tmpId,_tmpPlayerId,_tmpTotalGames,_tmpGamesWon,_tmpTotalScore,_tmpHighestScore,_tmpTotalLinesCleared,_tmpHighestLinesInGame,_tmpTotalPiecesPlaced,_tmpHighestLevel,_tmpTotalTimePlayed,_tmpLongestGame,_tmpFastestClearTime,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpComboMax,_tmpBackToBackCount,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpLastPlayed,_tmpCurrentStreak,_tmpLongestStreak,_tmpAchievement,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTopStatistics(final int limit,
      final Continuation<? super List<Statistics>> $completion) {
    final String _sql = "SELECT * FROM statistics ORDER BY highestScore DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Statistics>>() {
      @Override
      @NonNull
      public List<Statistics> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfGamesWon = CursorUtil.getColumnIndexOrThrow(_cursor, "gamesWon");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highestScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfHighestLinesInGame = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLinesInGame");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfLongestGame = CursorUtil.getColumnIndexOrThrow(_cursor, "longestGame");
          final int _cursorIndexOfFastestClearTime = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestClearTime");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfComboMax = CursorUtil.getColumnIndexOrThrow(_cursor, "comboMax");
          final int _cursorIndexOfBackToBackCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBackCount");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfLastPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayed");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "achievement");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final List<Statistics> _result = new ArrayList<Statistics>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Statistics _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final int _tmpGamesWon;
            _tmpGamesWon = _cursor.getInt(_cursorIndexOfGamesWon);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighestScore;
            _tmpHighestScore = _cursor.getLong(_cursorIndexOfHighestScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpHighestLinesInGame;
            _tmpHighestLinesInGame = _cursor.getInt(_cursorIndexOfHighestLinesInGame);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final long _tmpLongestGame;
            _tmpLongestGame = _cursor.getLong(_cursorIndexOfLongestGame);
            final Long _tmpFastestClearTime;
            if (_cursor.isNull(_cursorIndexOfFastestClearTime)) {
              _tmpFastestClearTime = null;
            } else {
              _tmpFastestClearTime = _cursor.getLong(_cursorIndexOfFastestClearTime);
            }
            final int _tmpSingleLines;
            _tmpSingleLines = _cursor.getInt(_cursorIndexOfSingleLines);
            final int _tmpDoubleLines;
            _tmpDoubleLines = _cursor.getInt(_cursorIndexOfDoubleLines);
            final int _tmpTripleLines;
            _tmpTripleLines = _cursor.getInt(_cursorIndexOfTripleLines);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTSpins;
            _tmpTSpins = _cursor.getInt(_cursorIndexOfTSpins);
            final int _tmpTSpinSingles;
            _tmpTSpinSingles = _cursor.getInt(_cursorIndexOfTSpinSingles);
            final int _tmpTSpinDoubles;
            _tmpTSpinDoubles = _cursor.getInt(_cursorIndexOfTSpinDoubles);
            final int _tmpTSpinTriples;
            _tmpTSpinTriples = _cursor.getInt(_cursorIndexOfTSpinTriples);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final int _tmpComboMax;
            _tmpComboMax = _cursor.getInt(_cursorIndexOfComboMax);
            final int _tmpBackToBackCount;
            _tmpBackToBackCount = _cursor.getInt(_cursorIndexOfBackToBackCount);
            final int _tmpIPiecesPlaced;
            _tmpIPiecesPlaced = _cursor.getInt(_cursorIndexOfIPiecesPlaced);
            final int _tmpJPiecesPlaced;
            _tmpJPiecesPlaced = _cursor.getInt(_cursorIndexOfJPiecesPlaced);
            final int _tmpLPiecesPlaced;
            _tmpLPiecesPlaced = _cursor.getInt(_cursorIndexOfLPiecesPlaced);
            final int _tmpOPiecesPlaced;
            _tmpOPiecesPlaced = _cursor.getInt(_cursorIndexOfOPiecesPlaced);
            final int _tmpSPiecesPlaced;
            _tmpSPiecesPlaced = _cursor.getInt(_cursorIndexOfSPiecesPlaced);
            final int _tmpTPiecesPlaced;
            _tmpTPiecesPlaced = _cursor.getInt(_cursorIndexOfTPiecesPlaced);
            final int _tmpZPiecesPlaced;
            _tmpZPiecesPlaced = _cursor.getInt(_cursorIndexOfZPiecesPlaced);
            final Date _tmpLastPlayed;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLastPlayed)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLastPlayed);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastPlayed = _tmp_1;
            }
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final int _tmpAchievement;
            _tmpAchievement = _cursor.getInt(_cursorIndexOfAchievement);
            final int _tmpRotations;
            _tmpRotations = _cursor.getInt(_cursorIndexOfRotations);
            final int _tmpMovesLeft;
            _tmpMovesLeft = _cursor.getInt(_cursorIndexOfMovesLeft);
            final int _tmpMovesRight;
            _tmpMovesRight = _cursor.getInt(_cursorIndexOfMovesRight);
            final int _tmpSoftDropCount;
            _tmpSoftDropCount = _cursor.getInt(_cursorIndexOfSoftDropCount);
            final int _tmpHardDropCount;
            _tmpHardDropCount = _cursor.getInt(_cursorIndexOfHardDropCount);
            final int _tmpHoldPieceCount;
            _tmpHoldPieceCount = _cursor.getInt(_cursorIndexOfHoldPieceCount);
            _item = new Statistics(_tmpId,_tmpPlayerId,_tmpTotalGames,_tmpGamesWon,_tmpTotalScore,_tmpHighestScore,_tmpTotalLinesCleared,_tmpHighestLinesInGame,_tmpTotalPiecesPlaced,_tmpHighestLevel,_tmpTotalTimePlayed,_tmpLongestGame,_tmpFastestClearTime,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpComboMax,_tmpBackToBackCount,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpLastPlayed,_tmpCurrentStreak,_tmpLongestStreak,_tmpAchievement,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageStats(
      final Continuation<? super StatisticsDao.AverageStats> $completion) {
    final String _sql = "SELECT AVG(totalGames) as avgGames, AVG(highestScore) as avgHighScore, AVG(totalLinesCleared) as avgLines, AVG(totalTimePlayed) as avgTimePlayed FROM statistics";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StatisticsDao.AverageStats>() {
      @Override
      @NonNull
      public StatisticsDao.AverageStats call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAvgGames = 0;
          final int _cursorIndexOfAvgHighScore = 1;
          final int _cursorIndexOfAvgLines = 2;
          final int _cursorIndexOfAvgTimePlayed = 3;
          final StatisticsDao.AverageStats _result;
          if (_cursor.moveToFirst()) {
            final float _tmpAvgGames;
            _tmpAvgGames = _cursor.getFloat(_cursorIndexOfAvgGames);
            final float _tmpAvgHighScore;
            _tmpAvgHighScore = _cursor.getFloat(_cursorIndexOfAvgHighScore);
            final float _tmpAvgLines;
            _tmpAvgLines = _cursor.getFloat(_cursorIndexOfAvgLines);
            final float _tmpAvgTimePlayed;
            _tmpAvgTimePlayed = _cursor.getFloat(_cursorIndexOfAvgTimePlayed);
            _result = new StatisticsDao.AverageStats(_tmpAvgGames,_tmpAvgHighScore,_tmpAvgLines,_tmpAvgTimePlayed);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLeaderboard(final int limit,
      final Continuation<? super List<StatisticsDao.LeaderboardEntry>> $completion) {
    final String _sql = "SELECT s.id, p.name as playerName, s.highestScore, s.highestLevel, s.highestLinesInGame, s.tetrisCount FROM statistics s INNER JOIN players p ON s.playerId = p.id ORDER BY s.highestScore DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<StatisticsDao.LeaderboardEntry>>() {
      @Override
      @NonNull
      public List<StatisticsDao.LeaderboardEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfPlayerName = 1;
          final int _cursorIndexOfHighestScore = 2;
          final int _cursorIndexOfHighestLevel = 3;
          final int _cursorIndexOfHighestLinesInGame = 4;
          final int _cursorIndexOfTetrisCount = 5;
          final List<StatisticsDao.LeaderboardEntry> _result = new ArrayList<StatisticsDao.LeaderboardEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StatisticsDao.LeaderboardEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpPlayerName;
            _tmpPlayerName = _cursor.getString(_cursorIndexOfPlayerName);
            final long _tmpHighestScore;
            _tmpHighestScore = _cursor.getLong(_cursorIndexOfHighestScore);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final int _tmpHighestLinesInGame;
            _tmpHighestLinesInGame = _cursor.getInt(_cursorIndexOfHighestLinesInGame);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            _item = new StatisticsDao.LeaderboardEntry(_tmpId,_tmpPlayerName,_tmpHighestScore,_tmpHighestLevel,_tmpHighestLinesInGame,_tmpTetrisCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getStatisticsForAchievements(final int tetrisThreshold,
      final int perfectClearThreshold, final int comboThreshold,
      final Continuation<? super List<Statistics>> $completion) {
    final String _sql = "SELECT * FROM statistics WHERE tetrisCount >= ? OR perfectClearCount >= ? OR comboMax >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tetrisThreshold);
    _argIndex = 2;
    _statement.bindLong(_argIndex, perfectClearThreshold);
    _argIndex = 3;
    _statement.bindLong(_argIndex, comboThreshold);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Statistics>>() {
      @Override
      @NonNull
      public List<Statistics> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfGamesWon = CursorUtil.getColumnIndexOrThrow(_cursor, "gamesWon");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highestScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfHighestLinesInGame = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLinesInGame");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfHighestLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "highestLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfLongestGame = CursorUtil.getColumnIndexOrThrow(_cursor, "longestGame");
          final int _cursorIndexOfFastestClearTime = CursorUtil.getColumnIndexOrThrow(_cursor, "fastestClearTime");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfComboMax = CursorUtil.getColumnIndexOrThrow(_cursor, "comboMax");
          final int _cursorIndexOfBackToBackCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBackCount");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfLastPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPlayed");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfAchievement = CursorUtil.getColumnIndexOrThrow(_cursor, "achievement");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final List<Statistics> _result = new ArrayList<Statistics>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Statistics _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final int _tmpGamesWon;
            _tmpGamesWon = _cursor.getInt(_cursorIndexOfGamesWon);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighestScore;
            _tmpHighestScore = _cursor.getLong(_cursorIndexOfHighestScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpHighestLinesInGame;
            _tmpHighestLinesInGame = _cursor.getInt(_cursorIndexOfHighestLinesInGame);
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final int _tmpHighestLevel;
            _tmpHighestLevel = _cursor.getInt(_cursorIndexOfHighestLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final long _tmpLongestGame;
            _tmpLongestGame = _cursor.getLong(_cursorIndexOfLongestGame);
            final Long _tmpFastestClearTime;
            if (_cursor.isNull(_cursorIndexOfFastestClearTime)) {
              _tmpFastestClearTime = null;
            } else {
              _tmpFastestClearTime = _cursor.getLong(_cursorIndexOfFastestClearTime);
            }
            final int _tmpSingleLines;
            _tmpSingleLines = _cursor.getInt(_cursorIndexOfSingleLines);
            final int _tmpDoubleLines;
            _tmpDoubleLines = _cursor.getInt(_cursorIndexOfDoubleLines);
            final int _tmpTripleLines;
            _tmpTripleLines = _cursor.getInt(_cursorIndexOfTripleLines);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTSpins;
            _tmpTSpins = _cursor.getInt(_cursorIndexOfTSpins);
            final int _tmpTSpinSingles;
            _tmpTSpinSingles = _cursor.getInt(_cursorIndexOfTSpinSingles);
            final int _tmpTSpinDoubles;
            _tmpTSpinDoubles = _cursor.getInt(_cursorIndexOfTSpinDoubles);
            final int _tmpTSpinTriples;
            _tmpTSpinTriples = _cursor.getInt(_cursorIndexOfTSpinTriples);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final int _tmpComboMax;
            _tmpComboMax = _cursor.getInt(_cursorIndexOfComboMax);
            final int _tmpBackToBackCount;
            _tmpBackToBackCount = _cursor.getInt(_cursorIndexOfBackToBackCount);
            final int _tmpIPiecesPlaced;
            _tmpIPiecesPlaced = _cursor.getInt(_cursorIndexOfIPiecesPlaced);
            final int _tmpJPiecesPlaced;
            _tmpJPiecesPlaced = _cursor.getInt(_cursorIndexOfJPiecesPlaced);
            final int _tmpLPiecesPlaced;
            _tmpLPiecesPlaced = _cursor.getInt(_cursorIndexOfLPiecesPlaced);
            final int _tmpOPiecesPlaced;
            _tmpOPiecesPlaced = _cursor.getInt(_cursorIndexOfOPiecesPlaced);
            final int _tmpSPiecesPlaced;
            _tmpSPiecesPlaced = _cursor.getInt(_cursorIndexOfSPiecesPlaced);
            final int _tmpTPiecesPlaced;
            _tmpTPiecesPlaced = _cursor.getInt(_cursorIndexOfTPiecesPlaced);
            final int _tmpZPiecesPlaced;
            _tmpZPiecesPlaced = _cursor.getInt(_cursorIndexOfZPiecesPlaced);
            final Date _tmpLastPlayed;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLastPlayed)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLastPlayed);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastPlayed = _tmp_1;
            }
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final int _tmpAchievement;
            _tmpAchievement = _cursor.getInt(_cursorIndexOfAchievement);
            final int _tmpRotations;
            _tmpRotations = _cursor.getInt(_cursorIndexOfRotations);
            final int _tmpMovesLeft;
            _tmpMovesLeft = _cursor.getInt(_cursorIndexOfMovesLeft);
            final int _tmpMovesRight;
            _tmpMovesRight = _cursor.getInt(_cursorIndexOfMovesRight);
            final int _tmpSoftDropCount;
            _tmpSoftDropCount = _cursor.getInt(_cursorIndexOfSoftDropCount);
            final int _tmpHardDropCount;
            _tmpHardDropCount = _cursor.getInt(_cursorIndexOfHardDropCount);
            final int _tmpHoldPieceCount;
            _tmpHoldPieceCount = _cursor.getInt(_cursorIndexOfHoldPieceCount);
            _item = new Statistics(_tmpId,_tmpPlayerId,_tmpTotalGames,_tmpGamesWon,_tmpTotalScore,_tmpHighestScore,_tmpTotalLinesCleared,_tmpHighestLinesInGame,_tmpTotalPiecesPlaced,_tmpHighestLevel,_tmpTotalTimePlayed,_tmpLongestGame,_tmpFastestClearTime,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpComboMax,_tmpBackToBackCount,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpLastPlayed,_tmpCurrentStreak,_tmpLongestStreak,_tmpAchievement,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPiecePlacementStats(final int playerId,
      final Continuation<? super StatisticsDao.PiecePlacementStats> $completion) {
    final String _sql = "SELECT iPiecesPlaced, jPiecesPlaced, lPiecesPlaced, oPiecesPlaced, sPiecesPlaced, tPiecesPlaced, zPiecesPlaced FROM statistics WHERE playerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StatisticsDao.PiecePlacementStats>() {
      @Override
      @Nullable
      public StatisticsDao.PiecePlacementStats call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIPiecesPlaced = 0;
          final int _cursorIndexOfJPiecesPlaced = 1;
          final int _cursorIndexOfLPiecesPlaced = 2;
          final int _cursorIndexOfOPiecesPlaced = 3;
          final int _cursorIndexOfSPiecesPlaced = 4;
          final int _cursorIndexOfTPiecesPlaced = 5;
          final int _cursorIndexOfZPiecesPlaced = 6;
          final StatisticsDao.PiecePlacementStats _result;
          if (_cursor.moveToFirst()) {
            final int _tmpIPiecesPlaced;
            _tmpIPiecesPlaced = _cursor.getInt(_cursorIndexOfIPiecesPlaced);
            final int _tmpJPiecesPlaced;
            _tmpJPiecesPlaced = _cursor.getInt(_cursorIndexOfJPiecesPlaced);
            final int _tmpLPiecesPlaced;
            _tmpLPiecesPlaced = _cursor.getInt(_cursorIndexOfLPiecesPlaced);
            final int _tmpOPiecesPlaced;
            _tmpOPiecesPlaced = _cursor.getInt(_cursorIndexOfOPiecesPlaced);
            final int _tmpSPiecesPlaced;
            _tmpSPiecesPlaced = _cursor.getInt(_cursorIndexOfSPiecesPlaced);
            final int _tmpTPiecesPlaced;
            _tmpTPiecesPlaced = _cursor.getInt(_cursorIndexOfTPiecesPlaced);
            final int _tmpZPiecesPlaced;
            _tmpZPiecesPlaced = _cursor.getInt(_cursorIndexOfZPiecesPlaced);
            _result = new StatisticsDao.PiecePlacementStats(_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMovementStats(final int playerId,
      final Continuation<? super StatisticsDao.MovementStats> $completion) {
    final String _sql = "SELECT rotations, movesLeft, movesRight, softDropCount, hardDropCount, holdPieceCount FROM statistics WHERE playerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StatisticsDao.MovementStats>() {
      @Override
      @Nullable
      public StatisticsDao.MovementStats call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRotations = 0;
          final int _cursorIndexOfMovesLeft = 1;
          final int _cursorIndexOfMovesRight = 2;
          final int _cursorIndexOfSoftDropCount = 3;
          final int _cursorIndexOfHardDropCount = 4;
          final int _cursorIndexOfHoldPieceCount = 5;
          final StatisticsDao.MovementStats _result;
          if (_cursor.moveToFirst()) {
            final int _tmpRotations;
            _tmpRotations = _cursor.getInt(_cursorIndexOfRotations);
            final int _tmpMovesLeft;
            _tmpMovesLeft = _cursor.getInt(_cursorIndexOfMovesLeft);
            final int _tmpMovesRight;
            _tmpMovesRight = _cursor.getInt(_cursorIndexOfMovesRight);
            final int _tmpSoftDropCount;
            _tmpSoftDropCount = _cursor.getInt(_cursorIndexOfSoftDropCount);
            final int _tmpHardDropCount;
            _tmpHardDropCount = _cursor.getInt(_cursorIndexOfHardDropCount);
            final int _tmpHoldPieceCount;
            _tmpHoldPieceCount = _cursor.getInt(_cursorIndexOfHoldPieceCount);
            _result = new StatisticsDao.MovementStats(_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

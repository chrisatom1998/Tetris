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
import com.tetris.model.GameMode;
import com.tetris.model.HighScore;
import java.lang.Boolean;
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
public final class ScoreDao_Impl implements ScoreDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HighScore> __insertionAdapterOfHighScore;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<HighScore> __deletionAdapterOfHighScore;

  private final EntityDeletionOrUpdateAdapter<HighScore> __updateAdapterOfHighScore;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExcessHighScores;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldHighScores;

  public ScoreDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHighScore = new EntityInsertionAdapter<HighScore>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `high_scores` (`id`,`playerId`,`score`,`levelReached`,`linesCleared`,`achievedAt`,`gameMode`,`tetrisCount`,`maxCombo`,`perfectClearCount`,`gameDuration`,`deviceModel`,`appVersion`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HighScore entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        statement.bindLong(3, entity.getScore());
        statement.bindLong(4, entity.getLevelReached());
        statement.bindLong(5, entity.getLinesCleared());
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getAchievedAt());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        statement.bindString(7, entity.getGameMode());
        statement.bindLong(8, entity.getTetrisCount());
        statement.bindLong(9, entity.getMaxCombo());
        statement.bindLong(10, entity.getPerfectClearCount());
        statement.bindLong(11, entity.getGameDuration());
        statement.bindString(12, entity.getDeviceModel());
        statement.bindString(13, entity.getAppVersion());
      }
    };
    this.__deletionAdapterOfHighScore = new EntityDeletionOrUpdateAdapter<HighScore>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `high_scores` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HighScore entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfHighScore = new EntityDeletionOrUpdateAdapter<HighScore>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `high_scores` SET `id` = ?,`playerId` = ?,`score` = ?,`levelReached` = ?,`linesCleared` = ?,`achievedAt` = ?,`gameMode` = ?,`tetrisCount` = ?,`maxCombo` = ?,`perfectClearCount` = ?,`gameDuration` = ?,`deviceModel` = ?,`appVersion` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HighScore entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        statement.bindLong(3, entity.getScore());
        statement.bindLong(4, entity.getLevelReached());
        statement.bindLong(5, entity.getLinesCleared());
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getAchievedAt());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        statement.bindString(7, entity.getGameMode());
        statement.bindLong(8, entity.getTetrisCount());
        statement.bindLong(9, entity.getMaxCombo());
        statement.bindLong(10, entity.getPerfectClearCount());
        statement.bindLong(11, entity.getGameDuration());
        statement.bindString(12, entity.getDeviceModel());
        statement.bindString(13, entity.getAppVersion());
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteExcessHighScores = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM high_scores WHERE id IN (SELECT id FROM high_scores WHERE playerId = ? ORDER BY score DESC LIMIT -1 OFFSET ?)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldHighScores = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM high_scores WHERE achievedAt < datetime('now', '-1 year')";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final HighScore highScore, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHighScore.insertAndReturnId(highScore);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final HighScore highScore, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHighScore.handle(highScore);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final HighScore highScore, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHighScore.handle(highScore);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object recordHighScore(final int playerId, final long score, final int levelReached,
      final int linesCleared, final GameMode gameMode, final int maximumScores,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> ScoreDao.DefaultImpls.recordHighScore(ScoreDao_Impl.this, playerId, score, levelReached, linesCleared, gameMode, maximumScores, __cont), $completion);
  }

  @Override
  public Object deleteExcessHighScores(final int playerId, final int maxScores,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExcessHighScores.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, playerId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, maxScores);
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
          __preparedStmtOfDeleteExcessHighScores.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldHighScores(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldHighScores.acquire();
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
          __preparedStmtOfDeleteOldHighScores.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getHighScoreById(final int id, final Continuation<? super HighScore> $completion) {
    final String _sql = "SELECT * FROM high_scores WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HighScore>() {
      @Override
      @Nullable
      public HighScore call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevelReached = CursorUtil.getColumnIndexOrThrow(_cursor, "levelReached");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfGameDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "gameDuration");
          final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceModel");
          final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
          final HighScore _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final long _tmpGameDuration;
            _tmpGameDuration = _cursor.getLong(_cursorIndexOfGameDuration);
            final String _tmpDeviceModel;
            _tmpDeviceModel = _cursor.getString(_cursorIndexOfDeviceModel);
            final String _tmpAppVersion;
            _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
            _result = new HighScore(_tmpId,_tmpPlayerId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpTetrisCount,_tmpMaxCombo,_tmpPerfectClearCount,_tmpGameDuration,_tmpDeviceModel,_tmpAppVersion);
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
  public Flow<List<HighScore>> getHighScoresForPlayer(final int playerId) {
    final String _sql = "SELECT * FROM high_scores WHERE playerId = ? ORDER BY score DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"high_scores"}, new Callable<List<HighScore>>() {
      @Override
      @NonNull
      public List<HighScore> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevelReached = CursorUtil.getColumnIndexOrThrow(_cursor, "levelReached");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfGameDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "gameDuration");
          final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceModel");
          final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
          final List<HighScore> _result = new ArrayList<HighScore>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighScore _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final long _tmpGameDuration;
            _tmpGameDuration = _cursor.getLong(_cursorIndexOfGameDuration);
            final String _tmpDeviceModel;
            _tmpDeviceModel = _cursor.getString(_cursorIndexOfDeviceModel);
            final String _tmpAppVersion;
            _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
            _item = new HighScore(_tmpId,_tmpPlayerId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpTetrisCount,_tmpMaxCombo,_tmpPerfectClearCount,_tmpGameDuration,_tmpDeviceModel,_tmpAppVersion);
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
  public Object getTopScoresForGameMode(final String gameMode, final int limit,
      final Continuation<? super List<HighScore>> $completion) {
    final String _sql = "SELECT * FROM high_scores WHERE gameMode = ? ORDER BY score DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, gameMode);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HighScore>>() {
      @Override
      @NonNull
      public List<HighScore> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevelReached = CursorUtil.getColumnIndexOrThrow(_cursor, "levelReached");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfGameDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "gameDuration");
          final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceModel");
          final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
          final List<HighScore> _result = new ArrayList<HighScore>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighScore _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final long _tmpGameDuration;
            _tmpGameDuration = _cursor.getLong(_cursorIndexOfGameDuration);
            final String _tmpDeviceModel;
            _tmpDeviceModel = _cursor.getString(_cursorIndexOfDeviceModel);
            final String _tmpAppVersion;
            _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
            _item = new HighScore(_tmpId,_tmpPlayerId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpTetrisCount,_tmpMaxCombo,_tmpPerfectClearCount,_tmpGameDuration,_tmpDeviceModel,_tmpAppVersion);
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
  public Object getTopScores(final int limit,
      final Continuation<? super List<HighScore>> $completion) {
    final String _sql = "SELECT * FROM high_scores ORDER BY score DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HighScore>>() {
      @Override
      @NonNull
      public List<HighScore> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevelReached = CursorUtil.getColumnIndexOrThrow(_cursor, "levelReached");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfGameDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "gameDuration");
          final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceModel");
          final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
          final List<HighScore> _result = new ArrayList<HighScore>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighScore _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final long _tmpGameDuration;
            _tmpGameDuration = _cursor.getLong(_cursorIndexOfGameDuration);
            final String _tmpDeviceModel;
            _tmpDeviceModel = _cursor.getString(_cursorIndexOfDeviceModel);
            final String _tmpAppVersion;
            _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
            _item = new HighScore(_tmpId,_tmpPlayerId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpTetrisCount,_tmpMaxCombo,_tmpPerfectClearCount,_tmpGameDuration,_tmpDeviceModel,_tmpAppVersion);
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
  public Object isHighScore(final int playerId, final long score, final int maximumScores,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT COUNT(*) < ? OR MIN(score) < ? FROM high_scores WHERE playerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, maximumScores);
    _argIndex = 2;
    _statement.bindLong(_argIndex, score);
    _argIndex = 3;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
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
  public Object getDetailedHighScores(final int limit,
      final Continuation<? super List<ScoreDao.DetailedHighScore>> $completion) {
    final String _sql = "SELECT hs.id, hs.score, hs.levelReached as levelReached, hs.linesCleared as linesCleared, hs.achievedAt as achievedAt, hs.gameMode, p.name as playerName FROM high_scores hs INNER JOIN players p ON hs.playerId = p.id ORDER BY hs.score DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScoreDao.DetailedHighScore>>() {
      @Override
      @NonNull
      public List<ScoreDao.DetailedHighScore> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfScore = 1;
          final int _cursorIndexOfLevelReached = 2;
          final int _cursorIndexOfLinesCleared = 3;
          final int _cursorIndexOfAchievedAt = 4;
          final int _cursorIndexOfGameMode = 5;
          final int _cursorIndexOfPlayerName = 6;
          final List<ScoreDao.DetailedHighScore> _result = new ArrayList<ScoreDao.DetailedHighScore>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScoreDao.DetailedHighScore _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final String _tmpPlayerName;
            _tmpPlayerName = _cursor.getString(_cursorIndexOfPlayerName);
            _item = new ScoreDao.DetailedHighScore(_tmpId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpPlayerName);
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
  public Object getPlayerBestScoreForMode(final int playerId, final String gameMode,
      final Continuation<? super HighScore> $completion) {
    final String _sql = "SELECT * FROM high_scores WHERE playerId = ? AND gameMode = ? ORDER BY score DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    _argIndex = 2;
    _statement.bindString(_argIndex, gameMode);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HighScore>() {
      @Override
      @Nullable
      public HighScore call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevelReached = CursorUtil.getColumnIndexOrThrow(_cursor, "levelReached");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfGameDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "gameDuration");
          final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceModel");
          final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
          final HighScore _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final long _tmpGameDuration;
            _tmpGameDuration = _cursor.getLong(_cursorIndexOfGameDuration);
            final String _tmpDeviceModel;
            _tmpDeviceModel = _cursor.getString(_cursorIndexOfDeviceModel);
            final String _tmpAppVersion;
            _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
            _result = new HighScore(_tmpId,_tmpPlayerId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpTetrisCount,_tmpMaxCombo,_tmpPerfectClearCount,_tmpGameDuration,_tmpDeviceModel,_tmpAppVersion);
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
  public Object getPlayerBestScore(final int playerId,
      final Continuation<? super HighScore> $completion) {
    final String _sql = "SELECT * FROM high_scores WHERE playerId = ? ORDER BY score DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HighScore>() {
      @Override
      @Nullable
      public HighScore call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevelReached = CursorUtil.getColumnIndexOrThrow(_cursor, "levelReached");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfGameDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "gameDuration");
          final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceModel");
          final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
          final HighScore _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevelReached;
            _tmpLevelReached = _cursor.getInt(_cursorIndexOfLevelReached);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final Date _tmpAchievedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAchievedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAchievedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpAchievedAt = _tmp_1;
            }
            final String _tmpGameMode;
            _tmpGameMode = _cursor.getString(_cursorIndexOfGameMode);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            final long _tmpGameDuration;
            _tmpGameDuration = _cursor.getLong(_cursorIndexOfGameDuration);
            final String _tmpDeviceModel;
            _tmpDeviceModel = _cursor.getString(_cursorIndexOfDeviceModel);
            final String _tmpAppVersion;
            _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
            _result = new HighScore(_tmpId,_tmpPlayerId,_tmpScore,_tmpLevelReached,_tmpLinesCleared,_tmpAchievedAt,_tmpGameMode,_tmpTetrisCount,_tmpMaxCombo,_tmpPerfectClearCount,_tmpGameDuration,_tmpDeviceModel,_tmpAppVersion);
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

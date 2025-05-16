package com.tetris.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tetris.data.converters.DateConverter;
import com.tetris.model.Player;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
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
public final class PlayerDao_Impl implements PlayerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Player> __insertionAdapterOfPlayer;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<Player> __deletionAdapterOfPlayer;

  private final EntityDeletionOrUpdateAdapter<Player> __updateAdapterOfPlayer;

  private final SharedSQLiteStatement __preparedStmtOfUpdateHighScoreIfHigher;

  private final SharedSQLiteStatement __preparedStmtOfIncrementGamesPlayed;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlayerStats;

  public PlayerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlayer = new EntityInsertionAdapter<Player>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `players` (`id`,`name`,`createdAt`,`totalGames`,`totalScore`,`highScore`,`totalLinesCleared`,`maxLevel`,`totalTimePlayed`,`tetrisCount`,`tspin`,`perfectClearCount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Player entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindLong(4, entity.getTotalGames());
        statement.bindLong(5, entity.getTotalScore());
        statement.bindLong(6, entity.getHighScore());
        statement.bindLong(7, entity.getTotalLinesCleared());
        statement.bindLong(8, entity.getMaxLevel());
        statement.bindLong(9, entity.getTotalTimePlayed());
        statement.bindLong(10, entity.getTetrisCount());
        statement.bindLong(11, entity.getTspin());
        statement.bindLong(12, entity.getPerfectClearCount());
      }
    };
    this.__deletionAdapterOfPlayer = new EntityDeletionOrUpdateAdapter<Player>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `players` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Player entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPlayer = new EntityDeletionOrUpdateAdapter<Player>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `players` SET `id` = ?,`name` = ?,`createdAt` = ?,`totalGames` = ?,`totalScore` = ?,`highScore` = ?,`totalLinesCleared` = ?,`maxLevel` = ?,`totalTimePlayed` = ?,`tetrisCount` = ?,`tspin` = ?,`perfectClearCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Player entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final Long _tmp = __dateConverter.dateToTimestamp(entity.getCreatedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindLong(4, entity.getTotalGames());
        statement.bindLong(5, entity.getTotalScore());
        statement.bindLong(6, entity.getHighScore());
        statement.bindLong(7, entity.getTotalLinesCleared());
        statement.bindLong(8, entity.getMaxLevel());
        statement.bindLong(9, entity.getTotalTimePlayed());
        statement.bindLong(10, entity.getTetrisCount());
        statement.bindLong(11, entity.getTspin());
        statement.bindLong(12, entity.getPerfectClearCount());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateHighScoreIfHigher = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE players SET highScore = ? WHERE id = ? AND highScore < ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementGamesPlayed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE players SET totalGames = totalGames + 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlayerStats = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE players SET totalScore = totalScore + ?, totalLinesCleared = totalLinesCleared + ?, totalTimePlayed = totalTimePlayed + ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Player player, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPlayer.insertAndReturnId(player);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Player player, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPlayer.handle(player);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Player player, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPlayer.handle(player);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
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
  public Object updatePlayerStats(final int playerId, final long score, final int lines,
      final long time, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlayerStats.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, score);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, lines);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, time);
        _argIndex = 4;
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
          __preparedStmtOfUpdatePlayerStats.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlayerById(final int playerId, final Continuation<? super Player> $completion) {
    final String _sql = "SELECT * FROM players WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Player>() {
      @Override
      @Nullable
      public Player call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfMaxLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "maxLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTspin = CursorUtil.getColumnIndexOrThrow(_cursor, "tspin");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final Player _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighScore;
            _tmpHighScore = _cursor.getLong(_cursorIndexOfHighScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpMaxLevel;
            _tmpMaxLevel = _cursor.getInt(_cursorIndexOfMaxLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTspin;
            _tmpTspin = _cursor.getInt(_cursorIndexOfTspin);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            _result = new Player(_tmpId,_tmpName,_tmpCreatedAt,_tmpTotalGames,_tmpTotalScore,_tmpHighScore,_tmpTotalLinesCleared,_tmpMaxLevel,_tmpTotalTimePlayed,_tmpTetrisCount,_tmpTspin,_tmpPerfectClearCount);
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
  public Object getPlayerByName(final String name, final Continuation<? super Player> $completion) {
    final String _sql = "SELECT * FROM players WHERE name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, name);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Player>() {
      @Override
      @Nullable
      public Player call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfMaxLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "maxLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTspin = CursorUtil.getColumnIndexOrThrow(_cursor, "tspin");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final Player _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighScore;
            _tmpHighScore = _cursor.getLong(_cursorIndexOfHighScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpMaxLevel;
            _tmpMaxLevel = _cursor.getInt(_cursorIndexOfMaxLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTspin;
            _tmpTspin = _cursor.getInt(_cursorIndexOfTspin);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            _result = new Player(_tmpId,_tmpName,_tmpCreatedAt,_tmpTotalGames,_tmpTotalScore,_tmpHighScore,_tmpTotalLinesCleared,_tmpMaxLevel,_tmpTotalTimePlayed,_tmpTetrisCount,_tmpTspin,_tmpPerfectClearCount);
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
  public Flow<List<Player>> getAllPlayers() {
    final String _sql = "SELECT * FROM players";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"players"}, new Callable<List<Player>>() {
      @Override
      @NonNull
      public List<Player> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfMaxLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "maxLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTspin = CursorUtil.getColumnIndexOrThrow(_cursor, "tspin");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final List<Player> _result = new ArrayList<Player>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Player _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighScore;
            _tmpHighScore = _cursor.getLong(_cursorIndexOfHighScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpMaxLevel;
            _tmpMaxLevel = _cursor.getInt(_cursorIndexOfMaxLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTspin;
            _tmpTspin = _cursor.getInt(_cursorIndexOfTspin);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            _item = new Player(_tmpId,_tmpName,_tmpCreatedAt,_tmpTotalGames,_tmpTotalScore,_tmpHighScore,_tmpTotalLinesCleared,_tmpMaxLevel,_tmpTotalTimePlayed,_tmpTetrisCount,_tmpTspin,_tmpPerfectClearCount);
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
  public Flow<List<Player>> getAllPlayersByScore() {
    final String _sql = "SELECT * FROM players ORDER BY highScore DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"players"}, new Callable<List<Player>>() {
      @Override
      @NonNull
      public List<Player> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfMaxLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "maxLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTspin = CursorUtil.getColumnIndexOrThrow(_cursor, "tspin");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final List<Player> _result = new ArrayList<Player>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Player _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighScore;
            _tmpHighScore = _cursor.getLong(_cursorIndexOfHighScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpMaxLevel;
            _tmpMaxLevel = _cursor.getInt(_cursorIndexOfMaxLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTspin;
            _tmpTspin = _cursor.getInt(_cursorIndexOfTspin);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            _item = new Player(_tmpId,_tmpName,_tmpCreatedAt,_tmpTotalGames,_tmpTotalScore,_tmpHighScore,_tmpTotalLinesCleared,_tmpMaxLevel,_tmpTotalTimePlayed,_tmpTetrisCount,_tmpTspin,_tmpPerfectClearCount);
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
  public Object getLastActivePlayer(final Continuation<? super Player> $completion) {
    final String _sql = "SELECT * FROM players ORDER BY id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Player>() {
      @Override
      @Nullable
      public Player call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfMaxLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "maxLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTspin = CursorUtil.getColumnIndexOrThrow(_cursor, "tspin");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final Player _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighScore;
            _tmpHighScore = _cursor.getLong(_cursorIndexOfHighScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpMaxLevel;
            _tmpMaxLevel = _cursor.getInt(_cursorIndexOfMaxLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTspin;
            _tmpTspin = _cursor.getInt(_cursorIndexOfTspin);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            _result = new Player(_tmpId,_tmpName,_tmpCreatedAt,_tmpTotalGames,_tmpTotalScore,_tmpHighScore,_tmpTotalLinesCleared,_tmpMaxLevel,_tmpTotalTimePlayed,_tmpTetrisCount,_tmpTspin,_tmpPerfectClearCount);
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
  public Object getTopPlayers(final int limit,
      final Continuation<? super List<Player>> $completion) {
    final String _sql = "SELECT * FROM players ORDER BY highScore DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Player>>() {
      @Override
      @NonNull
      public List<Player> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTotalGames = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGames");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfHighScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highScore");
          final int _cursorIndexOfTotalLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "totalLinesCleared");
          final int _cursorIndexOfMaxLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "maxLevel");
          final int _cursorIndexOfTotalTimePlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimePlayed");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTspin = CursorUtil.getColumnIndexOrThrow(_cursor, "tspin");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final List<Player> _result = new ArrayList<Player>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Player _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Date _tmpCreatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_1 = __dateConverter.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_1;
            }
            final int _tmpTotalGames;
            _tmpTotalGames = _cursor.getInt(_cursorIndexOfTotalGames);
            final long _tmpTotalScore;
            _tmpTotalScore = _cursor.getLong(_cursorIndexOfTotalScore);
            final long _tmpHighScore;
            _tmpHighScore = _cursor.getLong(_cursorIndexOfHighScore);
            final int _tmpTotalLinesCleared;
            _tmpTotalLinesCleared = _cursor.getInt(_cursorIndexOfTotalLinesCleared);
            final int _tmpMaxLevel;
            _tmpMaxLevel = _cursor.getInt(_cursorIndexOfMaxLevel);
            final long _tmpTotalTimePlayed;
            _tmpTotalTimePlayed = _cursor.getLong(_cursorIndexOfTotalTimePlayed);
            final int _tmpTetrisCount;
            _tmpTetrisCount = _cursor.getInt(_cursorIndexOfTetrisCount);
            final int _tmpTspin;
            _tmpTspin = _cursor.getInt(_cursorIndexOfTspin);
            final int _tmpPerfectClearCount;
            _tmpPerfectClearCount = _cursor.getInt(_cursorIndexOfPerfectClearCount);
            _item = new Player(_tmpId,_tmpName,_tmpCreatedAt,_tmpTotalGames,_tmpTotalScore,_tmpHighScore,_tmpTotalLinesCleared,_tmpMaxLevel,_tmpTotalTimePlayed,_tmpTetrisCount,_tmpTspin,_tmpPerfectClearCount);
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
  public Object getPlayerCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM players";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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

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
import com.tetris.model.Achievement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
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
public final class AchievementDao_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Achievement> __insertionAdapterOfAchievement;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<Achievement> __deletionAdapterOfAchievement;

  private final EntityDeletionOrUpdateAdapter<Achievement> __updateAdapterOfAchievement;

  private final SharedSQLiteStatement __preparedStmtOfUnlockAchievement;

  public AchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAchievement = new EntityInsertionAdapter<Achievement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `achievements` (`id`,`playerId`,`achievementId`,`isUnlocked`,`unlockedAt`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Achievement entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        statement.bindString(3, entity.getAchievementId());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final Long _tmp_1 = __dateConverter.dateToTimestamp(entity.getUnlockedAt());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfAchievement = new EntityDeletionOrUpdateAdapter<Achievement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `achievements` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Achievement entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAchievement = new EntityDeletionOrUpdateAdapter<Achievement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `achievements` SET `id` = ?,`playerId` = ?,`achievementId` = ?,`isUnlocked` = ?,`unlockedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Achievement entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        statement.bindString(3, entity.getAchievementId());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final Long _tmp_1 = __dateConverter.dateToTimestamp(entity.getUnlockedAt());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp_1);
        }
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfUnlockAchievement = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE achievements SET isUnlocked = 1, unlockedAt = ? WHERE playerId = ? AND achievementId = ? AND isUnlocked = 0";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Achievement achievement,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAchievement.insertAndReturnId(achievement);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Achievement achievement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAchievement.handle(achievement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Achievement achievement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAchievement.handle(achievement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object ensurePlayerHasAchievements(final int playerId, final List<String> achievementIds,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AchievementDao.DefaultImpls.ensurePlayerHasAchievements(AchievementDao_Impl.this, playerId, achievementIds, __cont), $completion);
  }

  @Override
  public Object trackAchievementProgress(final int playerId, final String achievementId,
      final int currentProgress, final int targetProgress,
      final Continuation<? super Boolean> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AchievementDao.DefaultImpls.trackAchievementProgress(AchievementDao_Impl.this, playerId, achievementId, currentProgress, targetProgress, __cont), $completion);
  }

  @Override
  public Object checkAchievementsBasedOnStats(final int playerId, final int totalGames,
      final long highScore, final int linesCleared, final int level, final int tetrisCount,
      final Continuation<? super List<String>> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> AchievementDao.DefaultImpls.checkAchievementsBasedOnStats(AchievementDao_Impl.this, playerId, totalGames, highScore, linesCleared, level, tetrisCount, __cont), $completion);
  }

  @Override
  public Object unlockAchievement(final int playerId, final String achievementId, final Date date,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUnlockAchievement.acquire();
        int _argIndex = 1;
        final Long _tmp = __dateConverter.dateToTimestamp(date);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, playerId);
        _argIndex = 3;
        _stmt.bindString(_argIndex, achievementId);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUnlockAchievement.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAchievementById(final int id,
      final Continuation<? super Achievement> $completion) {
    final String _sql = "SELECT * FROM achievements WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Achievement>() {
      @Override
      @Nullable
      public Achievement call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final Achievement _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            _result = new Achievement(_tmpId,_tmpPlayerId,_tmpAchievementId,_tmpIsUnlocked,_tmpUnlockedAt);
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
  public Object getPlayerAchievement(final int playerId, final String achievementId,
      final Continuation<? super Achievement> $completion) {
    final String _sql = "SELECT * FROM achievements WHERE playerId = ? AND achievementId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    _argIndex = 2;
    _statement.bindString(_argIndex, achievementId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Achievement>() {
      @Override
      @Nullable
      public Achievement call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final Achievement _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            _result = new Achievement(_tmpId,_tmpPlayerId,_tmpAchievementId,_tmpIsUnlocked,_tmpUnlockedAt);
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
  public Flow<List<Achievement>> getPlayerAchievements(final int playerId) {
    final String _sql = "SELECT * FROM achievements WHERE playerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<Achievement>>() {
      @Override
      @NonNull
      public List<Achievement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final List<Achievement> _result = new ArrayList<Achievement>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Achievement _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            _item = new Achievement(_tmpId,_tmpPlayerId,_tmpAchievementId,_tmpIsUnlocked,_tmpUnlockedAt);
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
  public Flow<List<Achievement>> getUnlockedAchievements(final int playerId) {
    final String _sql = "SELECT * FROM achievements WHERE playerId = ? AND isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<Achievement>>() {
      @Override
      @NonNull
      public List<Achievement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final List<Achievement> _result = new ArrayList<Achievement>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Achievement _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            _item = new Achievement(_tmpId,_tmpPlayerId,_tmpAchievementId,_tmpIsUnlocked,_tmpUnlockedAt);
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
  public Flow<List<Achievement>> getLockedAchievements(final int playerId) {
    final String _sql = "SELECT * FROM achievements WHERE playerId = ? AND isUnlocked = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<Achievement>>() {
      @Override
      @NonNull
      public List<Achievement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final List<Achievement> _result = new ArrayList<Achievement>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Achievement _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            _item = new Achievement(_tmpId,_tmpPlayerId,_tmpAchievementId,_tmpIsUnlocked,_tmpUnlockedAt);
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
  public Object countUnlockedAchievements(final int playerId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM achievements WHERE playerId = ? AND isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
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

  @Override
  public Object hasAchievement(final int playerId, final String achievementId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT isUnlocked FROM achievements WHERE playerId = ? AND achievementId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    _argIndex = 2;
    _statement.bindString(_argIndex, achievementId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @Nullable
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
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
  public Object getRecentAchievements(final int playerId, final int limit,
      final Continuation<? super List<Achievement>> $completion) {
    final String _sql = "SELECT * FROM achievements WHERE playerId = ? AND isUnlocked = 1 ORDER BY unlockedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Achievement>>() {
      @Override
      @NonNull
      public List<Achievement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfAchievementId = CursorUtil.getColumnIndexOrThrow(_cursor, "achievementId");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final List<Achievement> _result = new ArrayList<Achievement>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Achievement _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            _item = new Achievement(_tmpId,_tmpPlayerId,_tmpAchievementId,_tmpIsUnlocked,_tmpUnlockedAt);
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
  public Flow<List<AchievementDao.AchievementProgress>> getAchievementProgressData(
      final int playerId) {
    final String _sql = "SELECT a.id, a.achievementId, a.playerId, a.isUnlocked, a.unlockedAt, ad.name, ad.description, ad.progressRequired, ad.iconResource FROM achievements a JOIN achievement_definitions ad ON a.achievementId = ad.id WHERE a.playerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements",
        "achievement_definitions"}, new Callable<List<AchievementDao.AchievementProgress>>() {
      @Override
      @NonNull
      public List<AchievementDao.AchievementProgress> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfAchievementId = 1;
          final int _cursorIndexOfPlayerId = 2;
          final int _cursorIndexOfIsUnlocked = 3;
          final int _cursorIndexOfUnlockedAt = 4;
          final int _cursorIndexOfName = 5;
          final int _cursorIndexOfDescription = 6;
          final int _cursorIndexOfProgressRequired = 7;
          final int _cursorIndexOfIconResource = 8;
          final List<AchievementDao.AchievementProgress> _result = new ArrayList<AchievementDao.AchievementProgress>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AchievementDao.AchievementProgress _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpAchievementId;
            _tmpAchievementId = _cursor.getString(_cursorIndexOfAchievementId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final Date _tmpUnlockedAt;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _tmpUnlockedAt = __dateConverter.fromTimestamp(_tmp_1);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpProgressRequired;
            _tmpProgressRequired = _cursor.getInt(_cursorIndexOfProgressRequired);
            final String _tmpIconResource;
            _tmpIconResource = _cursor.getString(_cursorIndexOfIconResource);
            _item = new AchievementDao.AchievementProgress(_tmpId,_tmpAchievementId,_tmpPlayerId,_tmpIsUnlocked,_tmpUnlockedAt,_tmpName,_tmpDescription,_tmpProgressRequired,_tmpIconResource);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

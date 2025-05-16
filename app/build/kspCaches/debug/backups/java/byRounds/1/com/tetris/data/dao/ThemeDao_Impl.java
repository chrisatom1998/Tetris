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
import com.tetris.data.converters.IntArrayConverter;
import com.tetris.model.Theme;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ThemeDao_Impl implements ThemeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Theme> __insertionAdapterOfTheme;

  private final EntityDeletionOrUpdateAdapter<Theme> __deletionAdapterOfTheme;

  private final EntityDeletionOrUpdateAdapter<Theme> __updateAdapterOfTheme;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateAllThemes;

  private final SharedSQLiteStatement __preparedStmtOfActivateTheme;

  private final SharedSQLiteStatement __preparedStmtOfUnlockTheme;

  public ThemeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTheme = new EntityInsertionAdapter<Theme>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `themes` (`id`,`name`,`description`,`backgroundColor`,`textColor`,`gridColor`,`highlightColor`,`blockStyle`,`backgroundImageRes`,`pieceColors`,`isUnlocked`,`unlockCriteria`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Theme entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getBackgroundColor());
        statement.bindLong(5, entity.getTextColor());
        statement.bindLong(6, entity.getGridColor());
        statement.bindLong(7, entity.getHighlightColor());
        statement.bindString(8, __BlockStyle_enumToString(entity.getBlockStyle()));
        if (entity.getBackgroundImageRes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getBackgroundImageRes());
        }
        final String _tmp = IntArrayConverter.INSTANCE.fromIntArray(entity.getPieceColors());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        statement.bindString(12, entity.getUnlockCriteria());
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
      }
    };
    this.__deletionAdapterOfTheme = new EntityDeletionOrUpdateAdapter<Theme>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `themes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Theme entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTheme = new EntityDeletionOrUpdateAdapter<Theme>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `themes` SET `id` = ?,`name` = ?,`description` = ?,`backgroundColor` = ?,`textColor` = ?,`gridColor` = ?,`highlightColor` = ?,`blockStyle` = ?,`backgroundImageRes` = ?,`pieceColors` = ?,`isUnlocked` = ?,`unlockCriteria` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Theme entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindLong(4, entity.getBackgroundColor());
        statement.bindLong(5, entity.getTextColor());
        statement.bindLong(6, entity.getGridColor());
        statement.bindLong(7, entity.getHighlightColor());
        statement.bindString(8, __BlockStyle_enumToString(entity.getBlockStyle()));
        if (entity.getBackgroundImageRes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getBackgroundImageRes());
        }
        final String _tmp = IntArrayConverter.INSTANCE.fromIntArray(entity.getPieceColors());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        final int _tmp_1 = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
        statement.bindString(12, entity.getUnlockCriteria());
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivateAllThemes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE themes SET isActive = 0";
        return _query;
      }
    };
    this.__preparedStmtOfActivateTheme = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE themes SET isActive = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUnlockTheme = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE themes SET isUnlocked = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Theme theme, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTheme.insertAndReturnId(theme);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Theme theme, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTheme.handle(theme);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Theme theme, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTheme.handle(theme);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setActiveTheme(final int themeId, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> ThemeDao.DefaultImpls.setActiveTheme(ThemeDao_Impl.this, themeId, __cont), $completion);
  }

  @Override
  public Object deactivateAllThemes(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateAllThemes.acquire();
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
          __preparedStmtOfDeactivateAllThemes.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object activateTheme(final int themeId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfActivateTheme.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, themeId);
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
          __preparedStmtOfActivateTheme.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object unlockTheme(final int themeId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUnlockTheme.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, themeId);
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
          __preparedStmtOfUnlockTheme.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getThemeById(final int id, final Continuation<? super Theme> $completion) {
    final String _sql = "SELECT * FROM themes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Theme>() {
      @Override
      @Nullable
      public Theme call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "textColor");
          final int _cursorIndexOfGridColor = CursorUtil.getColumnIndexOrThrow(_cursor, "gridColor");
          final int _cursorIndexOfHighlightColor = CursorUtil.getColumnIndexOrThrow(_cursor, "highlightColor");
          final int _cursorIndexOfBlockStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "blockStyle");
          final int _cursorIndexOfBackgroundImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundImageRes");
          final int _cursorIndexOfPieceColors = CursorUtil.getColumnIndexOrThrow(_cursor, "pieceColors");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockCriteria = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockCriteria");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Theme _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getInt(_cursorIndexOfBackgroundColor);
            final int _tmpTextColor;
            _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
            final int _tmpGridColor;
            _tmpGridColor = _cursor.getInt(_cursorIndexOfGridColor);
            final int _tmpHighlightColor;
            _tmpHighlightColor = _cursor.getInt(_cursorIndexOfHighlightColor);
            final Theme.BlockStyle _tmpBlockStyle;
            _tmpBlockStyle = __BlockStyle_stringToEnum(_cursor.getString(_cursorIndexOfBlockStyle));
            final String _tmpBackgroundImageRes;
            if (_cursor.isNull(_cursorIndexOfBackgroundImageRes)) {
              _tmpBackgroundImageRes = null;
            } else {
              _tmpBackgroundImageRes = _cursor.getString(_cursorIndexOfBackgroundImageRes);
            }
            final int[] _tmpPieceColors;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPieceColors)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPieceColors);
            }
            final int[] _tmp_1 = IntArrayConverter.INSTANCE.toIntArray(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'int[]', but it was NULL.");
            } else {
              _tmpPieceColors = _tmp_1;
            }
            final boolean _tmpIsUnlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_2 != 0;
            final String _tmpUnlockCriteria;
            _tmpUnlockCriteria = _cursor.getString(_cursorIndexOfUnlockCriteria);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _result = new Theme(_tmpId,_tmpName,_tmpDescription,_tmpBackgroundColor,_tmpTextColor,_tmpGridColor,_tmpHighlightColor,_tmpBlockStyle,_tmpBackgroundImageRes,_tmpPieceColors,_tmpIsUnlocked,_tmpUnlockCriteria,_tmpIsActive);
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
  public Object getThemeByName(final String name, final Continuation<? super Theme> $completion) {
    final String _sql = "SELECT * FROM themes WHERE name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, name);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Theme>() {
      @Override
      @Nullable
      public Theme call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "textColor");
          final int _cursorIndexOfGridColor = CursorUtil.getColumnIndexOrThrow(_cursor, "gridColor");
          final int _cursorIndexOfHighlightColor = CursorUtil.getColumnIndexOrThrow(_cursor, "highlightColor");
          final int _cursorIndexOfBlockStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "blockStyle");
          final int _cursorIndexOfBackgroundImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundImageRes");
          final int _cursorIndexOfPieceColors = CursorUtil.getColumnIndexOrThrow(_cursor, "pieceColors");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockCriteria = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockCriteria");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Theme _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getInt(_cursorIndexOfBackgroundColor);
            final int _tmpTextColor;
            _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
            final int _tmpGridColor;
            _tmpGridColor = _cursor.getInt(_cursorIndexOfGridColor);
            final int _tmpHighlightColor;
            _tmpHighlightColor = _cursor.getInt(_cursorIndexOfHighlightColor);
            final Theme.BlockStyle _tmpBlockStyle;
            _tmpBlockStyle = __BlockStyle_stringToEnum(_cursor.getString(_cursorIndexOfBlockStyle));
            final String _tmpBackgroundImageRes;
            if (_cursor.isNull(_cursorIndexOfBackgroundImageRes)) {
              _tmpBackgroundImageRes = null;
            } else {
              _tmpBackgroundImageRes = _cursor.getString(_cursorIndexOfBackgroundImageRes);
            }
            final int[] _tmpPieceColors;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPieceColors)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPieceColors);
            }
            final int[] _tmp_1 = IntArrayConverter.INSTANCE.toIntArray(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'int[]', but it was NULL.");
            } else {
              _tmpPieceColors = _tmp_1;
            }
            final boolean _tmpIsUnlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_2 != 0;
            final String _tmpUnlockCriteria;
            _tmpUnlockCriteria = _cursor.getString(_cursorIndexOfUnlockCriteria);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _result = new Theme(_tmpId,_tmpName,_tmpDescription,_tmpBackgroundColor,_tmpTextColor,_tmpGridColor,_tmpHighlightColor,_tmpBlockStyle,_tmpBackgroundImageRes,_tmpPieceColors,_tmpIsUnlocked,_tmpUnlockCriteria,_tmpIsActive);
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
  public Flow<List<Theme>> getAllThemes() {
    final String _sql = "SELECT * FROM themes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"themes"}, new Callable<List<Theme>>() {
      @Override
      @NonNull
      public List<Theme> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "textColor");
          final int _cursorIndexOfGridColor = CursorUtil.getColumnIndexOrThrow(_cursor, "gridColor");
          final int _cursorIndexOfHighlightColor = CursorUtil.getColumnIndexOrThrow(_cursor, "highlightColor");
          final int _cursorIndexOfBlockStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "blockStyle");
          final int _cursorIndexOfBackgroundImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundImageRes");
          final int _cursorIndexOfPieceColors = CursorUtil.getColumnIndexOrThrow(_cursor, "pieceColors");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockCriteria = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockCriteria");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Theme> _result = new ArrayList<Theme>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Theme _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getInt(_cursorIndexOfBackgroundColor);
            final int _tmpTextColor;
            _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
            final int _tmpGridColor;
            _tmpGridColor = _cursor.getInt(_cursorIndexOfGridColor);
            final int _tmpHighlightColor;
            _tmpHighlightColor = _cursor.getInt(_cursorIndexOfHighlightColor);
            final Theme.BlockStyle _tmpBlockStyle;
            _tmpBlockStyle = __BlockStyle_stringToEnum(_cursor.getString(_cursorIndexOfBlockStyle));
            final String _tmpBackgroundImageRes;
            if (_cursor.isNull(_cursorIndexOfBackgroundImageRes)) {
              _tmpBackgroundImageRes = null;
            } else {
              _tmpBackgroundImageRes = _cursor.getString(_cursorIndexOfBackgroundImageRes);
            }
            final int[] _tmpPieceColors;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPieceColors)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPieceColors);
            }
            final int[] _tmp_1 = IntArrayConverter.INSTANCE.toIntArray(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'int[]', but it was NULL.");
            } else {
              _tmpPieceColors = _tmp_1;
            }
            final boolean _tmpIsUnlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_2 != 0;
            final String _tmpUnlockCriteria;
            _tmpUnlockCriteria = _cursor.getString(_cursorIndexOfUnlockCriteria);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _item = new Theme(_tmpId,_tmpName,_tmpDescription,_tmpBackgroundColor,_tmpTextColor,_tmpGridColor,_tmpHighlightColor,_tmpBlockStyle,_tmpBackgroundImageRes,_tmpPieceColors,_tmpIsUnlocked,_tmpUnlockCriteria,_tmpIsActive);
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
  public Flow<List<Theme>> getUnlockedThemes() {
    final String _sql = "SELECT * FROM themes WHERE isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"themes"}, new Callable<List<Theme>>() {
      @Override
      @NonNull
      public List<Theme> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "textColor");
          final int _cursorIndexOfGridColor = CursorUtil.getColumnIndexOrThrow(_cursor, "gridColor");
          final int _cursorIndexOfHighlightColor = CursorUtil.getColumnIndexOrThrow(_cursor, "highlightColor");
          final int _cursorIndexOfBlockStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "blockStyle");
          final int _cursorIndexOfBackgroundImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundImageRes");
          final int _cursorIndexOfPieceColors = CursorUtil.getColumnIndexOrThrow(_cursor, "pieceColors");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockCriteria = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockCriteria");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Theme> _result = new ArrayList<Theme>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Theme _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getInt(_cursorIndexOfBackgroundColor);
            final int _tmpTextColor;
            _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
            final int _tmpGridColor;
            _tmpGridColor = _cursor.getInt(_cursorIndexOfGridColor);
            final int _tmpHighlightColor;
            _tmpHighlightColor = _cursor.getInt(_cursorIndexOfHighlightColor);
            final Theme.BlockStyle _tmpBlockStyle;
            _tmpBlockStyle = __BlockStyle_stringToEnum(_cursor.getString(_cursorIndexOfBlockStyle));
            final String _tmpBackgroundImageRes;
            if (_cursor.isNull(_cursorIndexOfBackgroundImageRes)) {
              _tmpBackgroundImageRes = null;
            } else {
              _tmpBackgroundImageRes = _cursor.getString(_cursorIndexOfBackgroundImageRes);
            }
            final int[] _tmpPieceColors;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPieceColors)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPieceColors);
            }
            final int[] _tmp_1 = IntArrayConverter.INSTANCE.toIntArray(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'int[]', but it was NULL.");
            } else {
              _tmpPieceColors = _tmp_1;
            }
            final boolean _tmpIsUnlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_2 != 0;
            final String _tmpUnlockCriteria;
            _tmpUnlockCriteria = _cursor.getString(_cursorIndexOfUnlockCriteria);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _item = new Theme(_tmpId,_tmpName,_tmpDescription,_tmpBackgroundColor,_tmpTextColor,_tmpGridColor,_tmpHighlightColor,_tmpBlockStyle,_tmpBackgroundImageRes,_tmpPieceColors,_tmpIsUnlocked,_tmpUnlockCriteria,_tmpIsActive);
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
  public Object getActiveTheme(final Continuation<? super Theme> $completion) {
    final String _sql = "SELECT * FROM themes WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Theme>() {
      @Override
      @Nullable
      public Theme call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfBackgroundColor = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundColor");
          final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "textColor");
          final int _cursorIndexOfGridColor = CursorUtil.getColumnIndexOrThrow(_cursor, "gridColor");
          final int _cursorIndexOfHighlightColor = CursorUtil.getColumnIndexOrThrow(_cursor, "highlightColor");
          final int _cursorIndexOfBlockStyle = CursorUtil.getColumnIndexOrThrow(_cursor, "blockStyle");
          final int _cursorIndexOfBackgroundImageRes = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundImageRes");
          final int _cursorIndexOfPieceColors = CursorUtil.getColumnIndexOrThrow(_cursor, "pieceColors");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfUnlockCriteria = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockCriteria");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Theme _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpBackgroundColor;
            _tmpBackgroundColor = _cursor.getInt(_cursorIndexOfBackgroundColor);
            final int _tmpTextColor;
            _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
            final int _tmpGridColor;
            _tmpGridColor = _cursor.getInt(_cursorIndexOfGridColor);
            final int _tmpHighlightColor;
            _tmpHighlightColor = _cursor.getInt(_cursorIndexOfHighlightColor);
            final Theme.BlockStyle _tmpBlockStyle;
            _tmpBlockStyle = __BlockStyle_stringToEnum(_cursor.getString(_cursorIndexOfBlockStyle));
            final String _tmpBackgroundImageRes;
            if (_cursor.isNull(_cursorIndexOfBackgroundImageRes)) {
              _tmpBackgroundImageRes = null;
            } else {
              _tmpBackgroundImageRes = _cursor.getString(_cursorIndexOfBackgroundImageRes);
            }
            final int[] _tmpPieceColors;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPieceColors)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPieceColors);
            }
            final int[] _tmp_1 = IntArrayConverter.INSTANCE.toIntArray(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'int[]', but it was NULL.");
            } else {
              _tmpPieceColors = _tmp_1;
            }
            final boolean _tmpIsUnlocked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp_2 != 0;
            final String _tmpUnlockCriteria;
            _tmpUnlockCriteria = _cursor.getString(_cursorIndexOfUnlockCriteria);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _result = new Theme(_tmpId,_tmpName,_tmpDescription,_tmpBackgroundColor,_tmpTextColor,_tmpGridColor,_tmpHighlightColor,_tmpBlockStyle,_tmpBackgroundImageRes,_tmpPieceColors,_tmpIsUnlocked,_tmpUnlockCriteria,_tmpIsActive);
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
  public Object isThemeUnlocked(final int themeId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT isUnlocked FROM themes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, themeId);
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
  public Object getThemeCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM themes";
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

  private String __BlockStyle_enumToString(@NonNull final Theme.BlockStyle _value) {
    switch (_value) {
      case FLAT: return "FLAT";
      case GRADIENT: return "GRADIENT";
      case _3D: return "_3D";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private Theme.BlockStyle __BlockStyle_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "FLAT": return Theme.BlockStyle.FLAT;
      case "GRADIENT": return Theme.BlockStyle.GRADIENT;
      case "_3D": return Theme.BlockStyle._3D;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

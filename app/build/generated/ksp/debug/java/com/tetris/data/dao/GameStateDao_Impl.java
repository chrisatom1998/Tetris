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
import com.tetris.data.converters.BoardConverter;
import com.tetris.data.converters.DateConverter;
import com.tetris.data.converters.PieceConverter;
import com.tetris.model.Board;
import com.tetris.model.GameMode;
import com.tetris.model.GameState;
import com.tetris.model.Piece;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GameStateDao_Impl implements GameStateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GameState> __insertionAdapterOfGameState;

  private final BoardConverter __boardConverter = new BoardConverter();

  private final PieceConverter __pieceConverter = new PieceConverter();

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter<GameState> __deletionAdapterOfGameState;

  private final EntityDeletionOrUpdateAdapter<GameState> __updateAdapterOfGameState;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByPlayerId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCompletedGamesForPlayer;

  private final SharedSQLiteStatement __preparedStmtOfMarkAllGamesAsOverForPlayer;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTimeElapsed;

  private final SharedSQLiteStatement __preparedStmtOfCleanupOldGames;

  public GameStateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGameState = new EntityInsertionAdapter<GameState>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `game_states` (`id`,`playerId`,`board`,`currentPiece`,`nextPiece`,`heldPiece`,`hasHeld`,`score`,`level`,`linesCleared`,`combo`,`backToBack`,`singleLines`,`doubleLines`,`tripleLines`,`tetrisCount`,`tSpins`,`tSpinSingles`,`tSpinDoubles`,`tSpinTriples`,`perfectClearCount`,`maxCombo`,`iPiecesPlaced`,`jPiecesPlaced`,`lPiecesPlaced`,`oPiecesPlaced`,`sPiecesPlaced`,`tPiecesPlaced`,`zPiecesPlaced`,`rotations`,`movesLeft`,`movesRight`,`softDropCount`,`hardDropCount`,`holdPieceCount`,`totalPiecesPlaced`,`timeElapsed`,`isGameOver`,`isPaused`,`lastUpdated`,`gameMode`,`randomSeed`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameState entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        final String _tmp = __boardConverter.fromBoard(entity.getBoard());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = __pieceConverter.fromPiece(entity.getCurrentPiece());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        final String _tmp_2 = __pieceConverter.fromPiece(entity.getNextPiece());
        if (_tmp_2 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_2);
        }
        final String _tmp_3 = __pieceConverter.fromPiece(entity.getHeldPiece());
        if (_tmp_3 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_3);
        }
        final int _tmp_4 = entity.getHasHeld() ? 1 : 0;
        statement.bindLong(7, _tmp_4);
        statement.bindLong(8, entity.getScore());
        statement.bindLong(9, entity.getLevel());
        statement.bindLong(10, entity.getLinesCleared());
        statement.bindLong(11, entity.getCombo());
        statement.bindLong(12, entity.getBackToBack());
        statement.bindLong(13, entity.getSingleLines());
        statement.bindLong(14, entity.getDoubleLines());
        statement.bindLong(15, entity.getTripleLines());
        statement.bindLong(16, entity.getTetrisCount());
        statement.bindLong(17, entity.getTSpins());
        statement.bindLong(18, entity.getTSpinSingles());
        statement.bindLong(19, entity.getTSpinDoubles());
        statement.bindLong(20, entity.getTSpinTriples());
        statement.bindLong(21, entity.getPerfectClearCount());
        statement.bindLong(22, entity.getMaxCombo());
        statement.bindLong(23, entity.getIPiecesPlaced());
        statement.bindLong(24, entity.getJPiecesPlaced());
        statement.bindLong(25, entity.getLPiecesPlaced());
        statement.bindLong(26, entity.getOPiecesPlaced());
        statement.bindLong(27, entity.getSPiecesPlaced());
        statement.bindLong(28, entity.getTPiecesPlaced());
        statement.bindLong(29, entity.getZPiecesPlaced());
        statement.bindLong(30, entity.getRotations());
        statement.bindLong(31, entity.getMovesLeft());
        statement.bindLong(32, entity.getMovesRight());
        statement.bindLong(33, entity.getSoftDropCount());
        statement.bindLong(34, entity.getHardDropCount());
        statement.bindLong(35, entity.getHoldPieceCount());
        statement.bindLong(36, entity.getTotalPiecesPlaced());
        statement.bindLong(37, entity.getTimeElapsed());
        final int _tmp_5 = entity.isGameOver() ? 1 : 0;
        statement.bindLong(38, _tmp_5);
        final int _tmp_6 = entity.isPaused() ? 1 : 0;
        statement.bindLong(39, _tmp_6);
        final Long _tmp_7 = __dateConverter.dateToTimestamp(entity.getLastUpdated());
        if (_tmp_7 == null) {
          statement.bindNull(40);
        } else {
          statement.bindLong(40, _tmp_7);
        }
        statement.bindString(41, __GameMode_enumToString(entity.getGameMode()));
        statement.bindLong(42, entity.getRandomSeed());
      }
    };
    this.__deletionAdapterOfGameState = new EntityDeletionOrUpdateAdapter<GameState>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `game_states` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameState entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfGameState = new EntityDeletionOrUpdateAdapter<GameState>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `game_states` SET `id` = ?,`playerId` = ?,`board` = ?,`currentPiece` = ?,`nextPiece` = ?,`heldPiece` = ?,`hasHeld` = ?,`score` = ?,`level` = ?,`linesCleared` = ?,`combo` = ?,`backToBack` = ?,`singleLines` = ?,`doubleLines` = ?,`tripleLines` = ?,`tetrisCount` = ?,`tSpins` = ?,`tSpinSingles` = ?,`tSpinDoubles` = ?,`tSpinTriples` = ?,`perfectClearCount` = ?,`maxCombo` = ?,`iPiecesPlaced` = ?,`jPiecesPlaced` = ?,`lPiecesPlaced` = ?,`oPiecesPlaced` = ?,`sPiecesPlaced` = ?,`tPiecesPlaced` = ?,`zPiecesPlaced` = ?,`rotations` = ?,`movesLeft` = ?,`movesRight` = ?,`softDropCount` = ?,`hardDropCount` = ?,`holdPieceCount` = ?,`totalPiecesPlaced` = ?,`timeElapsed` = ?,`isGameOver` = ?,`isPaused` = ?,`lastUpdated` = ?,`gameMode` = ?,`randomSeed` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameState entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayerId());
        final String _tmp = __boardConverter.fromBoard(entity.getBoard());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = __pieceConverter.fromPiece(entity.getCurrentPiece());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        final String _tmp_2 = __pieceConverter.fromPiece(entity.getNextPiece());
        if (_tmp_2 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_2);
        }
        final String _tmp_3 = __pieceConverter.fromPiece(entity.getHeldPiece());
        if (_tmp_3 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_3);
        }
        final int _tmp_4 = entity.getHasHeld() ? 1 : 0;
        statement.bindLong(7, _tmp_4);
        statement.bindLong(8, entity.getScore());
        statement.bindLong(9, entity.getLevel());
        statement.bindLong(10, entity.getLinesCleared());
        statement.bindLong(11, entity.getCombo());
        statement.bindLong(12, entity.getBackToBack());
        statement.bindLong(13, entity.getSingleLines());
        statement.bindLong(14, entity.getDoubleLines());
        statement.bindLong(15, entity.getTripleLines());
        statement.bindLong(16, entity.getTetrisCount());
        statement.bindLong(17, entity.getTSpins());
        statement.bindLong(18, entity.getTSpinSingles());
        statement.bindLong(19, entity.getTSpinDoubles());
        statement.bindLong(20, entity.getTSpinTriples());
        statement.bindLong(21, entity.getPerfectClearCount());
        statement.bindLong(22, entity.getMaxCombo());
        statement.bindLong(23, entity.getIPiecesPlaced());
        statement.bindLong(24, entity.getJPiecesPlaced());
        statement.bindLong(25, entity.getLPiecesPlaced());
        statement.bindLong(26, entity.getOPiecesPlaced());
        statement.bindLong(27, entity.getSPiecesPlaced());
        statement.bindLong(28, entity.getTPiecesPlaced());
        statement.bindLong(29, entity.getZPiecesPlaced());
        statement.bindLong(30, entity.getRotations());
        statement.bindLong(31, entity.getMovesLeft());
        statement.bindLong(32, entity.getMovesRight());
        statement.bindLong(33, entity.getSoftDropCount());
        statement.bindLong(34, entity.getHardDropCount());
        statement.bindLong(35, entity.getHoldPieceCount());
        statement.bindLong(36, entity.getTotalPiecesPlaced());
        statement.bindLong(37, entity.getTimeElapsed());
        final int _tmp_5 = entity.isGameOver() ? 1 : 0;
        statement.bindLong(38, _tmp_5);
        final int _tmp_6 = entity.isPaused() ? 1 : 0;
        statement.bindLong(39, _tmp_6);
        final Long _tmp_7 = __dateConverter.dateToTimestamp(entity.getLastUpdated());
        if (_tmp_7 == null) {
          statement.bindNull(40);
        } else {
          statement.bindLong(40, _tmp_7);
        }
        statement.bindString(41, __GameMode_enumToString(entity.getGameMode()));
        statement.bindLong(42, entity.getRandomSeed());
        statement.bindLong(43, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByPlayerId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM game_states WHERE playerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCompletedGamesForPlayer = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM game_states WHERE playerId = ? AND isGameOver = 1";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAllGamesAsOverForPlayer = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE game_states SET isGameOver = 1 WHERE playerId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTimeElapsed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE game_states SET timeElapsed = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfCleanupOldGames = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM game_states WHERE lastUpdated < datetime('now', '-7 days')";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final GameState gameState, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfGameState.insertAndReturnId(gameState);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final GameState gameState, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfGameState.handle(gameState);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final GameState gameState, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGameState.handle(gameState);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrUpdate(final GameState gameState,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> GameStateDao.DefaultImpls.insertOrUpdate(GameStateDao_Impl.this, gameState, __cont), $completion);
  }

  @Override
  public Object deleteByPlayerId(final int playerId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByPlayerId.acquire();
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
          __preparedStmtOfDeleteByPlayerId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCompletedGamesForPlayer(final int playerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCompletedGamesForPlayer.acquire();
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
          __preparedStmtOfDeleteCompletedGamesForPlayer.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAllGamesAsOverForPlayer(final int playerId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAllGamesAsOverForPlayer.acquire();
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
          __preparedStmtOfMarkAllGamesAsOverForPlayer.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTimeElapsed(final int gameStateId, final long timeElapsed,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTimeElapsed.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timeElapsed);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, gameStateId);
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
          __preparedStmtOfUpdateTimeElapsed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object cleanupOldGames(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCleanupOldGames.acquire();
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
          __preparedStmtOfCleanupOldGames.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getGameStateById(final int id, final Continuation<? super GameState> $completion) {
    final String _sql = "SELECT * FROM game_states WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GameState>() {
      @Override
      @Nullable
      public GameState call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfCurrentPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPiece");
          final int _cursorIndexOfNextPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "nextPiece");
          final int _cursorIndexOfHeldPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "heldPiece");
          final int _cursorIndexOfHasHeld = CursorUtil.getColumnIndexOrThrow(_cursor, "hasHeld");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "combo");
          final int _cursorIndexOfBackToBack = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBack");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfTimeElapsed = CursorUtil.getColumnIndexOrThrow(_cursor, "timeElapsed");
          final int _cursorIndexOfIsGameOver = CursorUtil.getColumnIndexOrThrow(_cursor, "isGameOver");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfRandomSeed = CursorUtil.getColumnIndexOrThrow(_cursor, "randomSeed");
          final GameState _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final Board _tmpBoard;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfBoard);
            }
            final Board _tmp_1 = __boardConverter.toBoard(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.tetris.model.Board', but it was NULL.");
            } else {
              _tmpBoard = _tmp_1;
            }
            final Piece _tmpCurrentPiece;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCurrentPiece)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfCurrentPiece);
            }
            _tmpCurrentPiece = __pieceConverter.toPiece(_tmp_2);
            final Piece _tmpNextPiece;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfNextPiece)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfNextPiece);
            }
            _tmpNextPiece = __pieceConverter.toPiece(_tmp_3);
            final Piece _tmpHeldPiece;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfHeldPiece)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfHeldPiece);
            }
            _tmpHeldPiece = __pieceConverter.toPiece(_tmp_4);
            final boolean _tmpHasHeld;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfHasHeld);
            _tmpHasHeld = _tmp_5 != 0;
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final int _tmpCombo;
            _tmpCombo = _cursor.getInt(_cursorIndexOfCombo);
            final int _tmpBackToBack;
            _tmpBackToBack = _cursor.getInt(_cursorIndexOfBackToBack);
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
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
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
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final long _tmpTimeElapsed;
            _tmpTimeElapsed = _cursor.getLong(_cursorIndexOfTimeElapsed);
            final boolean _tmpIsGameOver;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsGameOver);
            _tmpIsGameOver = _tmp_6 != 0;
            final boolean _tmpIsPaused;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_7 != 0;
            final Date _tmpLastUpdated;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_9;
            }
            final GameMode _tmpGameMode;
            _tmpGameMode = __GameMode_stringToEnum(_cursor.getString(_cursorIndexOfGameMode));
            final long _tmpRandomSeed;
            _tmpRandomSeed = _cursor.getLong(_cursorIndexOfRandomSeed);
            _result = new GameState(_tmpId,_tmpPlayerId,_tmpBoard,_tmpCurrentPiece,_tmpNextPiece,_tmpHeldPiece,_tmpHasHeld,_tmpScore,_tmpLevel,_tmpLinesCleared,_tmpCombo,_tmpBackToBack,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpMaxCombo,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount,_tmpTotalPiecesPlaced,_tmpTimeElapsed,_tmpIsGameOver,_tmpIsPaused,_tmpLastUpdated,_tmpGameMode,_tmpRandomSeed);
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
  public Object getActiveGameStateForPlayer(final int playerId,
      final Continuation<? super GameState> $completion) {
    final String _sql = "SELECT * FROM game_states WHERE playerId = ? AND isGameOver = 0 ORDER BY lastUpdated DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GameState>() {
      @Override
      @Nullable
      public GameState call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfCurrentPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPiece");
          final int _cursorIndexOfNextPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "nextPiece");
          final int _cursorIndexOfHeldPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "heldPiece");
          final int _cursorIndexOfHasHeld = CursorUtil.getColumnIndexOrThrow(_cursor, "hasHeld");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "combo");
          final int _cursorIndexOfBackToBack = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBack");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfTimeElapsed = CursorUtil.getColumnIndexOrThrow(_cursor, "timeElapsed");
          final int _cursorIndexOfIsGameOver = CursorUtil.getColumnIndexOrThrow(_cursor, "isGameOver");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfRandomSeed = CursorUtil.getColumnIndexOrThrow(_cursor, "randomSeed");
          final GameState _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final Board _tmpBoard;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfBoard);
            }
            final Board _tmp_1 = __boardConverter.toBoard(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.tetris.model.Board', but it was NULL.");
            } else {
              _tmpBoard = _tmp_1;
            }
            final Piece _tmpCurrentPiece;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCurrentPiece)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfCurrentPiece);
            }
            _tmpCurrentPiece = __pieceConverter.toPiece(_tmp_2);
            final Piece _tmpNextPiece;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfNextPiece)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfNextPiece);
            }
            _tmpNextPiece = __pieceConverter.toPiece(_tmp_3);
            final Piece _tmpHeldPiece;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfHeldPiece)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfHeldPiece);
            }
            _tmpHeldPiece = __pieceConverter.toPiece(_tmp_4);
            final boolean _tmpHasHeld;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfHasHeld);
            _tmpHasHeld = _tmp_5 != 0;
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final int _tmpCombo;
            _tmpCombo = _cursor.getInt(_cursorIndexOfCombo);
            final int _tmpBackToBack;
            _tmpBackToBack = _cursor.getInt(_cursorIndexOfBackToBack);
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
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
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
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final long _tmpTimeElapsed;
            _tmpTimeElapsed = _cursor.getLong(_cursorIndexOfTimeElapsed);
            final boolean _tmpIsGameOver;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsGameOver);
            _tmpIsGameOver = _tmp_6 != 0;
            final boolean _tmpIsPaused;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_7 != 0;
            final Date _tmpLastUpdated;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_9;
            }
            final GameMode _tmpGameMode;
            _tmpGameMode = __GameMode_stringToEnum(_cursor.getString(_cursorIndexOfGameMode));
            final long _tmpRandomSeed;
            _tmpRandomSeed = _cursor.getLong(_cursorIndexOfRandomSeed);
            _result = new GameState(_tmpId,_tmpPlayerId,_tmpBoard,_tmpCurrentPiece,_tmpNextPiece,_tmpHeldPiece,_tmpHasHeld,_tmpScore,_tmpLevel,_tmpLinesCleared,_tmpCombo,_tmpBackToBack,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpMaxCombo,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount,_tmpTotalPiecesPlaced,_tmpTimeElapsed,_tmpIsGameOver,_tmpIsPaused,_tmpLastUpdated,_tmpGameMode,_tmpRandomSeed);
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
  public Flow<List<GameState>> getAllGameStatesForPlayer(final int playerId) {
    final String _sql = "SELECT * FROM game_states WHERE playerId = ? ORDER BY lastUpdated DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, playerId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"game_states"}, new Callable<List<GameState>>() {
      @Override
      @NonNull
      public List<GameState> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayerId = CursorUtil.getColumnIndexOrThrow(_cursor, "playerId");
          final int _cursorIndexOfBoard = CursorUtil.getColumnIndexOrThrow(_cursor, "board");
          final int _cursorIndexOfCurrentPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPiece");
          final int _cursorIndexOfNextPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "nextPiece");
          final int _cursorIndexOfHeldPiece = CursorUtil.getColumnIndexOrThrow(_cursor, "heldPiece");
          final int _cursorIndexOfHasHeld = CursorUtil.getColumnIndexOrThrow(_cursor, "hasHeld");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfLinesCleared = CursorUtil.getColumnIndexOrThrow(_cursor, "linesCleared");
          final int _cursorIndexOfCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "combo");
          final int _cursorIndexOfBackToBack = CursorUtil.getColumnIndexOrThrow(_cursor, "backToBack");
          final int _cursorIndexOfSingleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "singleLines");
          final int _cursorIndexOfDoubleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "doubleLines");
          final int _cursorIndexOfTripleLines = CursorUtil.getColumnIndexOrThrow(_cursor, "tripleLines");
          final int _cursorIndexOfTetrisCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tetrisCount");
          final int _cursorIndexOfTSpins = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpins");
          final int _cursorIndexOfTSpinSingles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinSingles");
          final int _cursorIndexOfTSpinDoubles = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinDoubles");
          final int _cursorIndexOfTSpinTriples = CursorUtil.getColumnIndexOrThrow(_cursor, "tSpinTriples");
          final int _cursorIndexOfPerfectClearCount = CursorUtil.getColumnIndexOrThrow(_cursor, "perfectClearCount");
          final int _cursorIndexOfMaxCombo = CursorUtil.getColumnIndexOrThrow(_cursor, "maxCombo");
          final int _cursorIndexOfIPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "iPiecesPlaced");
          final int _cursorIndexOfJPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "jPiecesPlaced");
          final int _cursorIndexOfLPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "lPiecesPlaced");
          final int _cursorIndexOfOPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "oPiecesPlaced");
          final int _cursorIndexOfSPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "sPiecesPlaced");
          final int _cursorIndexOfTPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "tPiecesPlaced");
          final int _cursorIndexOfZPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "zPiecesPlaced");
          final int _cursorIndexOfRotations = CursorUtil.getColumnIndexOrThrow(_cursor, "rotations");
          final int _cursorIndexOfMovesLeft = CursorUtil.getColumnIndexOrThrow(_cursor, "movesLeft");
          final int _cursorIndexOfMovesRight = CursorUtil.getColumnIndexOrThrow(_cursor, "movesRight");
          final int _cursorIndexOfSoftDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "softDropCount");
          final int _cursorIndexOfHardDropCount = CursorUtil.getColumnIndexOrThrow(_cursor, "hardDropCount");
          final int _cursorIndexOfHoldPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "holdPieceCount");
          final int _cursorIndexOfTotalPiecesPlaced = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPiecesPlaced");
          final int _cursorIndexOfTimeElapsed = CursorUtil.getColumnIndexOrThrow(_cursor, "timeElapsed");
          final int _cursorIndexOfIsGameOver = CursorUtil.getColumnIndexOrThrow(_cursor, "isGameOver");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfGameMode = CursorUtil.getColumnIndexOrThrow(_cursor, "gameMode");
          final int _cursorIndexOfRandomSeed = CursorUtil.getColumnIndexOrThrow(_cursor, "randomSeed");
          final List<GameState> _result = new ArrayList<GameState>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameState _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpPlayerId;
            _tmpPlayerId = _cursor.getInt(_cursorIndexOfPlayerId);
            final Board _tmpBoard;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfBoard)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfBoard);
            }
            final Board _tmp_1 = __boardConverter.toBoard(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.tetris.model.Board', but it was NULL.");
            } else {
              _tmpBoard = _tmp_1;
            }
            final Piece _tmpCurrentPiece;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCurrentPiece)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfCurrentPiece);
            }
            _tmpCurrentPiece = __pieceConverter.toPiece(_tmp_2);
            final Piece _tmpNextPiece;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfNextPiece)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfNextPiece);
            }
            _tmpNextPiece = __pieceConverter.toPiece(_tmp_3);
            final Piece _tmpHeldPiece;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfHeldPiece)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfHeldPiece);
            }
            _tmpHeldPiece = __pieceConverter.toPiece(_tmp_4);
            final boolean _tmpHasHeld;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfHasHeld);
            _tmpHasHeld = _tmp_5 != 0;
            final long _tmpScore;
            _tmpScore = _cursor.getLong(_cursorIndexOfScore);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpLinesCleared;
            _tmpLinesCleared = _cursor.getInt(_cursorIndexOfLinesCleared);
            final int _tmpCombo;
            _tmpCombo = _cursor.getInt(_cursorIndexOfCombo);
            final int _tmpBackToBack;
            _tmpBackToBack = _cursor.getInt(_cursorIndexOfBackToBack);
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
            final int _tmpMaxCombo;
            _tmpMaxCombo = _cursor.getInt(_cursorIndexOfMaxCombo);
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
            final int _tmpTotalPiecesPlaced;
            _tmpTotalPiecesPlaced = _cursor.getInt(_cursorIndexOfTotalPiecesPlaced);
            final long _tmpTimeElapsed;
            _tmpTimeElapsed = _cursor.getLong(_cursorIndexOfTimeElapsed);
            final boolean _tmpIsGameOver;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsGameOver);
            _tmpIsGameOver = _tmp_6 != 0;
            final boolean _tmpIsPaused;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp_7 != 0;
            final Date _tmpLastUpdated;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_9 = __dateConverter.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_9;
            }
            final GameMode _tmpGameMode;
            _tmpGameMode = __GameMode_stringToEnum(_cursor.getString(_cursorIndexOfGameMode));
            final long _tmpRandomSeed;
            _tmpRandomSeed = _cursor.getLong(_cursorIndexOfRandomSeed);
            _item = new GameState(_tmpId,_tmpPlayerId,_tmpBoard,_tmpCurrentPiece,_tmpNextPiece,_tmpHeldPiece,_tmpHasHeld,_tmpScore,_tmpLevel,_tmpLinesCleared,_tmpCombo,_tmpBackToBack,_tmpSingleLines,_tmpDoubleLines,_tmpTripleLines,_tmpTetrisCount,_tmpTSpins,_tmpTSpinSingles,_tmpTSpinDoubles,_tmpTSpinTriples,_tmpPerfectClearCount,_tmpMaxCombo,_tmpIPiecesPlaced,_tmpJPiecesPlaced,_tmpLPiecesPlaced,_tmpOPiecesPlaced,_tmpSPiecesPlaced,_tmpTPiecesPlaced,_tmpZPiecesPlaced,_tmpRotations,_tmpMovesLeft,_tmpMovesRight,_tmpSoftDropCount,_tmpHardDropCount,_tmpHoldPieceCount,_tmpTotalPiecesPlaced,_tmpTimeElapsed,_tmpIsGameOver,_tmpIsPaused,_tmpLastUpdated,_tmpGameMode,_tmpRandomSeed);
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
  public Object countActiveGamesForPlayer(final int playerId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM game_states WHERE playerId = ? AND isGameOver = 0";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __GameMode_enumToString(@NonNull final GameMode _value) {
    switch (_value) {
      case MARATHON: return "MARATHON";
      case SPRINT: return "SPRINT";
      case ULTRA: return "ULTRA";
      case BATTLE: return "BATTLE";
      case PRACTICE: return "PRACTICE";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private GameMode __GameMode_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "MARATHON": return GameMode.MARATHON;
      case "SPRINT": return GameMode.SPRINT;
      case "ULTRA": return GameMode.ULTRA;
      case "BATTLE": return GameMode.BATTLE;
      case "PRACTICE": return GameMode.PRACTICE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

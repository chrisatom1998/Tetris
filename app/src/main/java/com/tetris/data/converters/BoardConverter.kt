package com.tetris.data.converters

import androidx.room.TypeConverter
import com.tetris.model.Board
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converter for Room database to convert between Board objects and JSON strings.
 * This allows Room to store the complex Board object in the database.
 */
class BoardConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromBoard(board: Board?): String? {
        if (board == null) return null
        
        // Create a 2D array of Integer colors representing the board cells
        val cells = Array(board.height) { y ->
            Array(board.width) { x ->
                board.cells[y][x]
            }
        }
        
        // Convert to JSON string
        return gson.toJson(BoardState(board.width, board.height, cells))
    }
    
    @TypeConverter
    fun toBoard(json: String?): Board? {
        if (json == null) return null
        
        // Parse the JSON into BoardState
        val type = object : TypeToken<BoardState>() {}.type
        val boardState = gson.fromJson<BoardState>(json, type)
        
        // Create a new Board and populate it
        val board = Board(boardState.width, boardState.height)
        
        // Copy cells from board state to the new board
        for (y in 0 until boardState.height) {
            for (x in 0 until boardState.width) {
                board.cells[y][x] = boardState.cells[y][x]
            }
        }
        
        return board
    }
    
    /**
     * Simple data class to serialize the board state
     */
    private data class BoardState(
        val width: Int,
        val height: Int,
        val cells: Array<Array<Int?>>
    ) {
        // Need to implement equals and hashCode because of Array usage
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            
            other as BoardState
            
            if (width != other.width) return false
            if (height != other.height) return false
            if (!cells.contentDeepEquals(other.cells)) return false
            
            return true
        }
        
        override fun hashCode(): Int {
            var result = width
            result = 31 * result + height
            result = 31 * result + cells.contentDeepHashCode()
            return result
        }
    }
}
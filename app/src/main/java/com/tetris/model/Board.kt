package com.tetris.model

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Represents the Tetris game board.
 * The board is a grid of cells, each of which can contain a block of a specific color.
 */
class Board(val width: Int = 10, val height: Int = 20) : Serializable {
    // 2D array representing the board cells
    // Each cell is either null (empty) or an Integer representing the color
    val cells: Array<Array<Int?>> = Array(height) { Array(width) { null } }
    
    /**
     * Adds a piece to the board permanently
     * @param piece The piece to add
     */
    fun addPiece(piece: Piece) {
        piece.blocks.forEach { block ->
            val x = piece.x + block.x
            val y = piece.y + block.y
            
            // Only add blocks that are within the board
            if (x >= 0 && x < width && y >= 0 && y < height) {
                cells[y][x] = piece.color
            }
        }
    }
    
    /**
     * Clears full lines and returns the number of lines cleared
     * @return The number of lines cleared
     */
    fun clearLines(): Int {
        var linesCleared = 0
        
        // Find and clear complete lines
        val linesToClear = mutableListOf<Int>()
        
        for (y in 0 until height) {
            var complete = true
            for (x in 0 until width) {
                if (cells[y][x] == null) {
                    complete = false
                    break
                }
            }
            if (complete) {
                linesToClear.add(y)
            }
        }
        
        // Clear lines and move blocks down
        linesToClear.sortedDescending().forEach { lineY ->
            // Clear the line
            for (x in 0 until width) {
                cells[lineY][x] = null
            }
            
            // Move all lines above down
            for (y in lineY downTo 1) {
                for (x in 0 until width) {
                    cells[y][x] = cells[y-1][x]
                }
            }
            
            // Clear the top line
            for (x in 0 until width) {
                cells[0][x] = null
            }
            
            linesCleared++
        }
        
        return linesCleared
    }
    
    /**
     * Checks if a piece would collide with anything on the board or go out of bounds
     * @param piece The piece to check
     * @param offsetX Horizontal offset to add to the piece position
     * @param offsetY Vertical offset to add to the piece position
     * @return True if the piece would collide, false otherwise
     */
    fun isCollision(piece: Piece, offsetX: Int = 0, offsetY: Int = 0): Boolean {
        piece.blocks.forEach { block ->
            val x = piece.x + block.x + offsetX
            val y = piece.y + block.y + offsetY
            
            // Check bounds
            if (x < 0 || x >= width || y >= height) {
                return true
            }
            
            // Check collision with existing blocks (if within the board)
            if (y >= 0 && cells[y][x] != null) {
                return true
            }
        }
        
        return false
    }
    
    /**
     * Checks if the piece can be moved in the specified direction
     * @param piece The piece to move
     * @param dx Horizontal movement (-1 for left, 1 for right)
     * @param dy Vertical movement (usually 1 for down)
     * @return True if the piece can move, false otherwise
     */
    fun canMove(piece: Piece, dx: Int, dy: Int): Boolean {
        return !isCollision(piece, dx, dy)
    }
    
    /**
     * Checks if the piece can be rotated
     * @param piece The piece to rotate
     * @param clockwise Whether to rotate clockwise or counter-clockwise
     * @return True if the piece can be rotated, false otherwise
     */
    fun canRotate(piece: Piece, clockwise: Boolean = true): Boolean {
        val rotatedPiece = piece.copy()
        if (clockwise) {
            rotatedPiece.rotateClockwise()
        } else {
            rotatedPiece.rotateCounterClockwise()
        }
        
        return !isCollision(rotatedPiece)
    }
    
    /**
     * Attempts to rotate a piece with wall kicks
     * @param piece The piece to rotate
     * @param clockwise Whether to rotate clockwise or counter-clockwise
     * @return True if the rotation was successful, false if it was blocked
     */
    fun tryRotate(piece: Piece, clockwise: Boolean = true): Boolean {
        // Try normal rotation first
        if (canRotate(piece, clockwise)) {
            if (clockwise) {
                piece.rotateClockwise()
            } else {
                piece.rotateCounterClockwise()
            }
            return true
        }
        
        // Try wall kicks - offsets to try when rotation is blocked
        val wallKicks = arrayOf(
            Pair(1, 0),   // right
            Pair(-1, 0),  // left
            Pair(0, -1),  // up
            Pair(1, -1),  // up-right
            Pair(-1, -1), // up-left
            Pair(2, 0),   // far right
            Pair(-2, 0)   // far left
        )
        
        // Create a copy of the piece and rotate it
        val rotatedPiece = piece.copy()
        if (clockwise) {
            rotatedPiece.rotateClockwise()
        } else {
            rotatedPiece.rotateCounterClockwise()
        }
        
        // Try each wall kick
        for ((kickX, kickY) in wallKicks) {
            if (!isCollision(rotatedPiece, kickX, kickY)) {
                // Wall kick successful, apply rotation and offset
                if (clockwise) {
                    piece.rotateClockwise()
                } else {
                    piece.rotateCounterClockwise()
                }
                piece.x += kickX
                piece.y += kickY
                return true
            }
        }
        
        // All wall kicks failed
        return false
    }
    
    /**
     * Check if the board has blocks in the top row, which indicates game over
     * @return True if the game is over
     */
    fun isGameOver(): Boolean {
        for (x in 0 until width) {
            if (cells[0][x] != null) {
                return true
            }
        }
        return false
    }
    
    /**
     * Clear the entire board
     */
    fun clear() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                cells[y][x] = null
            }
        }
    }
    
    /**
     * Create a deep copy of the board
     * @return A new board with the same state
     */
    fun copy(): Board {
        val newBoard = Board(width, height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                newBoard.cells[y][x] = cells[y][x]
            }
        }
        return newBoard
    }
    
    companion object {
        private const val serialVersionUID = 1L
    }
}
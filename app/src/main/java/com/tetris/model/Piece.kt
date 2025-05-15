package com.tetris.model

import android.graphics.Color
import android.graphics.Point

/**
 * Represents a Tetris piece (tetromino).
 * Each piece has a type, position, rotation state, and color.
 */
class Piece(val type: PieceType) {
    // Current position of the piece on the board (top-left corner)
    var x: Int = 0
    var y: Int = 0
    
    // Current rotation state (0-3)
    var rotation: Int = 0
    
    // Calculate blocks based on piece type and rotation
    val blocks: Array<Point>
        get() = type.getBlocks(rotation)
    
    // Color of the piece
    val color: Int
        get() = type.color
    
    /**
     * Creates a copy of this piece
     */
    fun copy(): Piece {
        return Piece(type).also {
            it.x = this.x
            it.y = this.y
            it.rotation = this.rotation
        }
    }
    
    /**
     * Rotate the piece clockwise
     */
    fun rotateClockwise() {
        rotation = (rotation + 1) % 4
    }
    
    /**
     * Rotate the piece counter-clockwise
     */
    fun rotateCounterClockwise() {
        rotation = (rotation + 3) % 4
    }
}

/**
 * Enumeration of the seven standard Tetris piece types.
 * Each type has a unique shape and color.
 */
enum class PieceType {
    I, // I-piece (cyan)
    J, // J-piece (blue)
    L, // L-piece (orange)
    O, // O-piece (yellow)
    S, // S-piece (green)
    T, // T-piece (purple)
    Z; // Z-piece (red)
    
    /**
     * Get blocks for the piece based on rotation state
     * @param rotation Rotation state (0-3)
     * @return Array of points representing block positions
     */
    fun getBlocks(rotation: Int): Array<Point> {
        return when (this) {
            I -> getIBlocks(rotation)
            J -> getJBlocks(rotation)
            L -> getLBlocks(rotation)
            O -> getOBlocks(rotation)
            S -> getSBlocks(rotation)
            T -> getTBlocks(rotation)
            Z -> getZBlocks(rotation)
        }
    }
    
    /**
     * Get the color for this piece type
     */
    val color: Int
        get() = when (this) {
            I -> Color.rgb(0, 240, 240)   // Cyan
            J -> Color.rgb(0, 0, 240)     // Blue
            L -> Color.rgb(240, 160, 0)   // Orange
            O -> Color.rgb(240, 240, 0)   // Yellow
            S -> Color.rgb(0, 240, 0)     // Green
            T -> Color.rgb(160, 0, 240)   // Purple
            Z -> Color.rgb(240, 0, 0)     // Red
        }
    
    /**
     * Helper methods to define the block positions for each piece type and rotation
     */
    private fun getIBlocks(rotation: Int): Array<Point> {
        return when (rotation % 2) {
            0 -> arrayOf(
                Point(0, 1), Point(1, 1), Point(2, 1), Point(3, 1)
            )
            1 -> arrayOf(
                Point(2, 0), Point(2, 1), Point(2, 2), Point(2, 3)
            )
            else -> throw IllegalArgumentException("Invalid rotation: $rotation")
        }
    }
    
    private fun getJBlocks(rotation: Int): Array<Point> {
        return when (rotation) {
            0 -> arrayOf(
                Point(0, 0), Point(0, 1), Point(1, 1), Point(2, 1)
            )
            1 -> arrayOf(
                Point(1, 0), Point(2, 0), Point(1, 1), Point(1, 2)
            )
            2 -> arrayOf(
                Point(0, 1), Point(1, 1), Point(2, 1), Point(2, 2)
            )
            3 -> arrayOf(
                Point(1, 0), Point(1, 1), Point(1, 2), Point(0, 2)
            )
            else -> throw IllegalArgumentException("Invalid rotation: $rotation")
        }
    }
    
    private fun getLBlocks(rotation: Int): Array<Point> {
        return when (rotation) {
            0 -> arrayOf(
                Point(2, 0), Point(0, 1), Point(1, 1), Point(2, 1)
            )
            1 -> arrayOf(
                Point(1, 0), Point(1, 1), Point(1, 2), Point(2, 2)
            )
            2 -> arrayOf(
                Point(0, 1), Point(1, 1), Point(2, 1), Point(0, 2)
            )
            3 -> arrayOf(
                Point(0, 0), Point(1, 0), Point(1, 1), Point(1, 2)
            )
            else -> throw IllegalArgumentException("Invalid rotation: $rotation")
        }
    }
    
    private fun getOBlocks(rotation: Int): Array<Point> {
        // O piece has the same shape in all rotations
        return arrayOf(
            Point(1, 0), Point(2, 0), Point(1, 1), Point(2, 1)
        )
    }
    
    private fun getSBlocks(rotation: Int): Array<Point> {
        return when (rotation % 2) {
            0 -> arrayOf(
                Point(1, 0), Point(2, 0), Point(0, 1), Point(1, 1)
            )
            1 -> arrayOf(
                Point(1, 0), Point(1, 1), Point(2, 1), Point(2, 2)
            )
            else -> throw IllegalArgumentException("Invalid rotation: $rotation")
        }
    }
    
    private fun getTBlocks(rotation: Int): Array<Point> {
        return when (rotation) {
            0 -> arrayOf(
                Point(1, 0), Point(0, 1), Point(1, 1), Point(2, 1)
            )
            1 -> arrayOf(
                Point(1, 0), Point(1, 1), Point(2, 1), Point(1, 2)
            )
            2 -> arrayOf(
                Point(0, 1), Point(1, 1), Point(2, 1), Point(1, 2)
            )
            3 -> arrayOf(
                Point(1, 0), Point(0, 1), Point(1, 1), Point(1, 2)
            )
            else -> throw IllegalArgumentException("Invalid rotation: $rotation")
        }
    }
    
    private fun getZBlocks(rotation: Int): Array<Point> {
        return when (rotation % 2) {
            0 -> arrayOf(
                Point(0, 0), Point(1, 0), Point(1, 1), Point(2, 1)
            )
            1 -> arrayOf(
                Point(2, 0), Point(1, 1), Point(2, 1), Point(1, 2)
            )
            else -> throw IllegalArgumentException("Invalid rotation: $rotation")
        }
    }
}
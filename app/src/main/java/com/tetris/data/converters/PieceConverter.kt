package com.tetris.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.tetris.model.Piece
import com.tetris.model.PieceType

/**
 * Type converter for Room database to convert between Piece objects and JSON strings.
 * This allows Room to store the complex Piece objects in the database.
 */
class PieceConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromPiece(piece: Piece?): String? {
        if (piece == null) return null
        
        // Create a serializable representation of the piece
        val pieceState = PieceState(
            type = piece.type.name,
            x = piece.x,
            y = piece.y,
            rotation = piece.rotation
        )
        
        // Convert to JSON string
        return gson.toJson(pieceState)
    }
    
    @TypeConverter
    fun toPiece(json: String?): Piece? {
        if (json == null) return null
        
        // Parse the JSON into PieceState
        val pieceState = gson.fromJson(json, PieceState::class.java)
        
        // Create a new Piece and set its properties
        val pieceType = PieceType.valueOf(pieceState.type)
        val piece = Piece(pieceType)
        
        piece.x = pieceState.x
        piece.y = pieceState.y
        
        // Apply rotations to get to the correct state
        // Need to use modulo in case rotation value is invalid
        val targetRotation = pieceState.rotation % 4
        for (i in 0 until targetRotation) {
            piece.rotateClockwise()
        }
        
        return piece
    }
    
    /**
     * Simple data class to serialize the piece state
     */
    private data class PieceState(
        val type: String,
        val x: Int,
        val y: Int,
        val rotation: Int
    )
}
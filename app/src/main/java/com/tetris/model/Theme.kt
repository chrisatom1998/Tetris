package com.tetris.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.graphics.Color

/**
 * Represents a visual theme for the Tetris game.
 * Themes define colors, block styles, backgrounds, and other visual aspects of the game.
 */
@Entity(tableName = "themes")
data class Theme(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // Theme name
    val name: String,
    
    // Theme description
    val description: String,
    
    // Background color
    val backgroundColor: Int,
    
    // Text color
    val textColor: Int,
    
    // Grid line color
    val gridColor: Int,
    
    // Highlight color for UI elements
    val highlightColor: Int,
    
    // Block style (flat, gradient, 3d)
    val blockStyle: BlockStyle = BlockStyle.FLAT,
    
    // Background image resource name (if any)
    val backgroundImageRes: String? = null,
    
    // Piece colors - array of 7 colors for I, J, L, O, S, T, Z pieces
    val pieceColors: IntArray,
    
    // Unlockable theme properties
    var isUnlocked: Boolean = false,
    var unlockCriteria: String = "",
    
    // Active theme flag
    var isActive: Boolean = false
) {
    /**
     * Get color for a specific piece type
     */
    fun getPieceColor(pieceType: Int): Int {
        return if (pieceType in 0..6) {
            pieceColors[pieceType]
        } else {
            Color.GRAY
        }
    }
    
    /**
     * Custom equals implementation needed because of IntArray
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as Theme
        
        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (backgroundColor != other.backgroundColor) return false
        if (textColor != other.textColor) return false
        if (gridColor != other.gridColor) return false
        if (highlightColor != other.highlightColor) return false
        if (blockStyle != other.blockStyle) return false
        if (backgroundImageRes != other.backgroundImageRes) return false
        if (!pieceColors.contentEquals(other.pieceColors)) return false
        if (isUnlocked != other.isUnlocked) return false
        if (unlockCriteria != other.unlockCriteria) return false
        if (isActive != other.isActive) return false
        
        return true
    }
    
    /**
     * Custom hashCode implementation needed because of IntArray
     */
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + backgroundColor
        result = 31 * result + textColor
        result = 31 * result + gridColor
        result = 31 * result + highlightColor
        result = 31 * result + blockStyle.hashCode()
        result = 31 * result + (backgroundImageRes?.hashCode() ?: 0)
        result = 31 * result + pieceColors.contentHashCode()
        result = 31 * result + isUnlocked.hashCode()
        result = 31 * result + unlockCriteria.hashCode()
        result = 31 * result + isActive.hashCode()
        return result
    }
    
    /**
     * Block rendering style
     */
    enum class BlockStyle {
        FLAT,    // Simple flat colored blocks
        GRADIENT, // Gradient colored blocks
        _3D      // 3D effect with highlights and shadows
    }
    
    companion object {
        /**
         * Create the default modern theme
         */
        fun createModernTheme(): Theme {
            return Theme(
                name = "Modern",
                description = "Clean, modern dark theme with vibrant colors",
                backgroundColor = Color.rgb(40, 44, 52),
                textColor = Color.WHITE,
                gridColor = Color.rgb(60, 65, 75),
                highlightColor = Color.rgb(97, 175, 239),
                blockStyle = BlockStyle._3D,
                pieceColors = intArrayOf(
                    Color.rgb(0, 240, 240),   // I - Cyan
                    Color.rgb(0, 0, 240),     // J - Blue
                    Color.rgb(240, 160, 0),   // L - Orange
                    Color.rgb(240, 240, 0),   // O - Yellow
                    Color.rgb(0, 240, 0),     // S - Green
                    Color.rgb(160, 0, 240),   // T - Purple
                    Color.rgb(240, 0, 0)      // Z - Red
                ),
                isUnlocked = true,
                isActive = true
            )
        }
        
        /**
         * Create the classic retro theme
         */
        fun createClassicTheme(): Theme {
            return Theme(
                name = "Classic",
                description = "Nostalgic theme reminiscent of the original Tetris",
                backgroundColor = Color.BLACK,
                textColor = Color.rgb(200, 200, 200),
                gridColor = Color.rgb(80, 80, 80),
                highlightColor = Color.rgb(255, 165, 0),
                blockStyle = BlockStyle.FLAT,
                pieceColors = intArrayOf(
                    Color.rgb(0, 255, 255),   // I - Cyan
                    Color.rgb(0, 0, 255),     // J - Blue
                    Color.rgb(255, 165, 0),   // L - Orange
                    Color.rgb(255, 255, 0),   // O - Yellow
                    Color.rgb(0, 255, 0),     // S - Green
                    Color.rgb(128, 0, 128),   // T - Purple
                    Color.rgb(255, 0, 0)      // Z - Red
                ),
                isUnlocked = true,
                isActive = false
            )
        }
        
        /**
         * Create minimalist theme
         */
        fun createMinimalistTheme(): Theme {
            return Theme(
                name = "Minimalist",
                description = "Clean, minimal design with soft colors",
                backgroundColor = Color.rgb(245, 245, 245),
                textColor = Color.rgb(60, 60, 60),
                gridColor = Color.rgb(220, 220, 220),
                highlightColor = Color.rgb(66, 139, 202),
                blockStyle = BlockStyle.FLAT,
                pieceColors = intArrayOf(
                    Color.rgb(120, 190, 190), // I - Light teal
                    Color.rgb(100, 120, 180), // J - Soft blue
                    Color.rgb(190, 160, 100), // L - Tan
                    Color.rgb(190, 190, 120), // O - Soft yellow
                    Color.rgb(120, 190, 120), // S - Soft green
                    Color.rgb(160, 120, 180), // T - Lavender
                    Color.rgb(190, 120, 120)  // Z - Soft red
                ),
                isUnlocked = false,
                unlockCriteria = "Clear 100 lines",
                isActive = false
            )
        }
        
        /**
         * Create high contrast accessibility theme
         */
        fun createHighContrastTheme(): Theme {
            return Theme(
                name = "High Contrast",
                description = "High visibility theme for accessibility",
                backgroundColor = Color.BLACK,
                textColor = Color.WHITE,
                gridColor = Color.rgb(150, 150, 150),
                highlightColor = Color.YELLOW,
                blockStyle = BlockStyle.FLAT,
                pieceColors = intArrayOf(
                    Color.rgb(0, 255, 255),   // I - Cyan
                    Color.rgb(0, 0, 255),     // J - Blue
                    Color.rgb(255, 165, 0),   // L - Orange
                    Color.rgb(255, 255, 0),   // O - Yellow
                    Color.rgb(0, 255, 0),     // S - Green
                    Color.rgb(255, 0, 255),   // T - Magenta
                    Color.rgb(255, 0, 0)      // Z - Red
                ),
                isUnlocked = false,
                unlockCriteria = "Play 50 games",
                isActive = false
            )
        }
        
        /**
         * Create a custom theme with default values
         */
        fun createCustomTheme(): Theme {
            return Theme(
                name = "Custom",
                description = "Your custom theme",
                backgroundColor = Color.rgb(30, 30, 30),
                textColor = Color.WHITE,
                gridColor = Color.DKGRAY,
                highlightColor = Color.rgb(97, 175, 239),
                blockStyle = BlockStyle._3D,
                pieceColors = intArrayOf(
                    Color.rgb(0, 240, 240),   // I - Cyan
                    Color.rgb(0, 0, 240),     // J - Blue
                    Color.rgb(240, 160, 0),   // L - Orange
                    Color.rgb(240, 240, 0),   // O - Yellow
                    Color.rgb(0, 240, 0),     // S - Green
                    Color.rgb(160, 0, 240),   // T - Purple
                    Color.rgb(240, 0, 0)      // Z - Red
                ),
                isUnlocked = false,
                unlockCriteria = "Reach level 15",
                isActive = false
            )
        }
    }
}
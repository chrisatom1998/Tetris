package com.tetris.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the visual themes for the Tetris game.
 * Handles loading, saving, and applying different themes.
 */
class ThemeManager private constructor(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val appContext = context.applicationContext
    
    // Currently selected theme
    private var currentTheme: Theme = Theme.MODERN
    
    // Cache of unlocked themes
    private val unlockedThemes = ConcurrentHashMap<Theme, Boolean>()
    
    // Cache of custom theme colors
    private var customBackgroundColor = DEFAULT_BG_COLOR
    private var customTextColor = DEFAULT_TEXT_COLOR
    private var customHighlightColor = DEFAULT_HIGHLIGHT_COLOR
    
    init {
        // Load saved preferences
        loadPreferences()
    }
    
    /**
     * Load saved theme preferences
     */
    private fun loadPreferences() {
        // Load current theme
        val themeName = prefs.getString(KEY_CURRENT_THEME, Theme.MODERN.name)
        currentTheme = try {
            Theme.valueOf(themeName ?: Theme.MODERN.name)
        } catch (e: IllegalArgumentException) {
            Theme.MODERN
        }
        
        // Load unlocked themes
        Theme.values().forEach { theme ->
            val isUnlocked = when (theme) {
                Theme.MODERN, Theme.CLASSIC -> true // Default unlocked themes
                else -> prefs.getBoolean(KEY_THEME_UNLOCKED_PREFIX + theme.name, false)
            }
            unlockedThemes[theme] = isUnlocked
        }
        
        // Load custom theme colors
        customBackgroundColor = prefs.getInt(KEY_CUSTOM_BG_COLOR, DEFAULT_BG_COLOR)
        customTextColor = prefs.getInt(KEY_CUSTOM_TEXT_COLOR, DEFAULT_TEXT_COLOR)
        customHighlightColor = prefs.getInt(KEY_CUSTOM_HIGHLIGHT_COLOR, DEFAULT_HIGHLIGHT_COLOR)
    }
    
    /**
     * Save current theme preferences
     */
    private fun savePreferences() {
        prefs.edit().apply {
            putString(KEY_CURRENT_THEME, currentTheme.name)
            
            // Save unlocked themes
            unlockedThemes.forEach { (theme, unlocked) ->
                putBoolean(KEY_THEME_UNLOCKED_PREFIX + theme.name, unlocked)
            }
            
            // Save custom theme colors
            putInt(KEY_CUSTOM_BG_COLOR, customBackgroundColor)
            putInt(KEY_CUSTOM_TEXT_COLOR, customTextColor)
            putInt(KEY_CUSTOM_HIGHLIGHT_COLOR, customHighlightColor)
        }.apply()
    }
    
    /**
     * Get the current theme
     */
    fun getCurrentTheme(): Theme {
        return currentTheme
    }
    
    /**
     * Set the current theme
     * @param theme The theme to set
     * @return true if the theme was set successfully, false if the theme is not unlocked
     */
    fun setCurrentTheme(theme: Theme): Boolean {
        if (!isThemeUnlocked(theme)) {
            return false
        }
        
        currentTheme = theme
        savePreferences()
        return true
    }
    
    /**
     * Check if a theme is unlocked
     */
    fun isThemeUnlocked(theme: Theme): Boolean {
        return unlockedThemes[theme] ?: false
    }
    
    /**
     * Unlock a theme
     */
    fun unlockTheme(theme: Theme) {
        unlockedThemes[theme] = true
        savePreferences()
    }
    
    /**
     * Get all unlocked themes
     */
    fun getUnlockedThemes(): List<Theme> {
        return Theme.values().filter { isThemeUnlocked(it) }
    }
    
    /**
     * Set custom theme colors
     */
    fun setCustomColors(@ColorInt backgroundColor: Int, @ColorInt textColor: Int, @ColorInt highlightColor: Int) {
        customBackgroundColor = backgroundColor
        customTextColor = textColor
        customHighlightColor = highlightColor
        savePreferences()
    }
    
    /**
     * Get the background color for the current theme
     */
    @ColorInt
    fun getBackgroundColor(): Int {
        return when (currentTheme) {
            Theme.MODERN -> Color.rgb(40, 44, 52)
            Theme.CLASSIC -> Color.BLACK
            Theme.MINIMALIST -> Color.rgb(245, 245, 245)
            Theme.HIGH_CONTRAST -> Color.BLACK
            Theme.CUSTOM -> customBackgroundColor
        }
    }
    
    /**
     * Get the text color for the current theme
     */
    @ColorInt
    fun getTextColor(): Int {
        return when (currentTheme) {
            Theme.MODERN -> Color.WHITE
            Theme.CLASSIC -> Color.rgb(200, 200, 200)
            Theme.MINIMALIST -> Color.rgb(60, 60, 60)
            Theme.HIGH_CONTRAST -> Color.WHITE
            Theme.CUSTOM -> customTextColor
        }
    }
    
    /**
     * Get the highlight color for the current theme
     */
    @ColorInt
    fun getHighlightColor(): Int {
        return when (currentTheme) {
            Theme.MODERN -> Color.rgb(97, 175, 239)
            Theme.CLASSIC -> Color.rgb(255, 165, 0) // Orange
            Theme.MINIMALIST -> Color.rgb(66, 139, 202) // Blue
            Theme.HIGH_CONTRAST -> Color.YELLOW
            Theme.CUSTOM -> customHighlightColor
        }
    }
    
    /**
     * Get the block color for a piece type based on the current theme
     */
    @ColorInt
    fun getBlockColor(pieceType: Int): Int {
        return when (currentTheme) {
            Theme.MODERN -> {
                when (pieceType) {
                    0 -> Color.rgb(0, 240, 240)   // I - Cyan
                    1 -> Color.rgb(0, 0, 240)     // J - Blue
                    2 -> Color.rgb(240, 160, 0)   // L - Orange
                    3 -> Color.rgb(240, 240, 0)   // O - Yellow
                    4 -> Color.rgb(0, 240, 0)     // S - Green
                    5 -> Color.rgb(160, 0, 240)   // T - Purple
                    6 -> Color.rgb(240, 0, 0)     // Z - Red
                    else -> Color.GRAY
                }
            }
            Theme.CLASSIC -> {
                when (pieceType) {
                    0 -> Color.rgb(0, 255, 255)   // I - Cyan
                    1 -> Color.rgb(0, 0, 255)     // J - Blue
                    2 -> Color.rgb(255, 165, 0)   // L - Orange
                    3 -> Color.rgb(255, 255, 0)   // O - Yellow
                    4 -> Color.rgb(0, 255, 0)     // S - Green
                    5 -> Color.rgb(128, 0, 128)   // T - Purple
                    6 -> Color.rgb(255, 0, 0)     // Z - Red
                    else -> Color.GRAY
                }
            }
            Theme.MINIMALIST -> {
                when (pieceType) {
                    0 -> Color.rgb(120, 190, 190) // I - Light teal
                    1 -> Color.rgb(100, 120, 180) // J - Soft blue
                    2 -> Color.rgb(190, 160, 100) // L - Tan
                    3 -> Color.rgb(190, 190, 120) // O - Soft yellow
                    4 -> Color.rgb(120, 190, 120) // S - Soft green
                    5 -> Color.rgb(160, 120, 180) // T - Lavender
                    6 -> Color.rgb(190, 120, 120) // Z - Soft red
                    else -> Color.GRAY
                }
            }
            Theme.HIGH_CONTRAST -> {
                when (pieceType) {
                    0 -> Color.rgb(0, 255, 255)   // I - Cyan
                    1 -> Color.rgb(0, 0, 255)     // J - Blue
                    2 -> Color.rgb(255, 165, 0)   // L - Orange
                    3 -> Color.rgb(255, 255, 0)   // O - Yellow
                    4 -> Color.rgb(0, 255, 0)     // S - Green
                    5 -> Color.rgb(255, 0, 255)   // T - Magenta
                    6 -> Color.rgb(255, 0, 0)     // Z - Red
                    else -> Color.WHITE
                }
            }
            Theme.CUSTOM -> {
                // For custom theme, we still use standard piece colors
                when (pieceType) {
                    0 -> Color.rgb(0, 240, 240)   // I - Cyan
                    1 -> Color.rgb(0, 0, 240)     // J - Blue
                    2 -> Color.rgb(240, 160, 0)   // L - Orange
                    3 -> Color.rgb(240, 240, 0)   // O - Yellow
                    4 -> Color.rgb(0, 240, 0)     // S - Green
                    5 -> Color.rgb(160, 0, 240)   // T - Purple
                    6 -> Color.rgb(240, 0, 0)     // Z - Red
                    else -> Color.GRAY
                }
            }
        }
    }
    
    /**
     * Get the grid line color for the current theme
     */
    @ColorInt
    fun getGridColor(): Int {
        return when (currentTheme) {
            Theme.MODERN -> Color.rgb(60, 65, 75)
            Theme.CLASSIC -> Color.rgb(80, 80, 80)
            Theme.MINIMALIST -> Color.rgb(220, 220, 220)
            Theme.HIGH_CONTRAST -> Color.rgb(150, 150, 150)
            Theme.CUSTOM -> Color.argb(80, 
                Color.red(customTextColor), 
                Color.green(customTextColor), 
                Color.blue(customTextColor))
        }
    }
    
    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: ThemeManager? = null
        
        /**
         * Get the singleton instance of ThemeManager
         */
        fun getInstance(context: Context): ThemeManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ThemeManager(context).also { INSTANCE = it }
            }
        }
        
        // Preference keys
        private const val PREF_NAME = "tetris_theme_prefs"
        private const val KEY_CURRENT_THEME = "current_theme"
        private const val KEY_THEME_UNLOCKED_PREFIX = "theme_unlocked_"
        private const val KEY_CUSTOM_BG_COLOR = "custom_bg_color"
        private const val KEY_CUSTOM_TEXT_COLOR = "custom_text_color"
        private const val KEY_CUSTOM_HIGHLIGHT_COLOR = "custom_highlight_color"
        
        // Default values
        private val DEFAULT_BG_COLOR = Color.rgb(30, 30, 30)
        private val DEFAULT_TEXT_COLOR = Color.WHITE
        private val DEFAULT_HIGHLIGHT_COLOR = Color.rgb(97, 175, 239)
    }
    
    /**
     * Available themes
     */
    enum class Theme {
        MODERN,      // Default modern dark theme
        CLASSIC,     // Retro theme reminiscent of original Tetris
        MINIMALIST,  // Clean, minimal design with soft colors
        HIGH_CONTRAST, // High visibility theme for accessibility
        CUSTOM       // User-customized theme
    }
}
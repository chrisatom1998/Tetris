package com.tetris.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.PowerManager
import android.util.Log

/**
 * Utility class for optimizing game performance based on battery status.
 * Helps extend battery life while providing a good gaming experience.
 */
class BatteryOptimizer(private val context: Context) {
    // Battery status information
    private var batteryLevel = 0
    private var isCharging = false
    private var isPowerSaveMode = false
    
    // Performance profiles
    enum class PerformanceProfile {
        HIGH,     // Full performance, all features enabled
        MEDIUM,   // Balanced performance with some optimizations
        LOW,      // Battery saving mode with reduced features
        CRITICAL  // Maximum battery saving with minimal features
    }
    
    init {
        // Initialize with current battery information
        updateBatteryInfo()
    }
    
    /**
     * Update battery information
     */
    fun updateBatteryInfo() {
        try {
            // Get battery status from system
            val batteryIntent = context.registerReceiver(null, 
                IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            
            batteryIntent?.let { intent ->
                // Get battery percentage
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                batteryLevel = if (level != -1 && scale != -1) (level * 100 / scale) else 50
                
                // Check if device is charging
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || 
                             status == BatteryManager.BATTERY_STATUS_FULL
            }
            
            // Check if device is in power save mode
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            isPowerSaveMode = powerManager.isPowerSaveMode
            
        } catch (e: Exception) {
            Log.e(TAG, "Error updating battery info", e)
            // Set default values
            batteryLevel = 50
            isCharging = false
            isPowerSaveMode = false
        }
    }
    
    /**
     * Get the current battery level (0-100)
     */
    fun getBatteryLevel(): Int {
        updateBatteryInfo()
        return batteryLevel
    }
    
    /**
     * Check if device is currently charging
     */
    fun isCharging(): Boolean {
        updateBatteryInfo()
        return isCharging
    }
    
    /**
     * Check if device is in power save mode
     */
    fun isPowerSaveMode(): Boolean {
        updateBatteryInfo()
        return isPowerSaveMode
    }
    
    /**
     * Get the recommended performance profile based on current battery status
     */
    fun getRecommendedPerformanceProfile(): PerformanceProfile {
        updateBatteryInfo()
        
        return when {
            isCharging -> PerformanceProfile.HIGH
            isPowerSaveMode -> PerformanceProfile.LOW
            batteryLevel < 15 -> PerformanceProfile.CRITICAL
            batteryLevel < 30 -> PerformanceProfile.LOW
            batteryLevel < 50 -> PerformanceProfile.MEDIUM
            else -> PerformanceProfile.HIGH
        }
    }
    
    /**
     * Get recommended frame rate based on battery status
     */
    fun getRecommendedFrameRate(): Int {
        return when (getRecommendedPerformanceProfile()) {
            PerformanceProfile.HIGH -> 60
            PerformanceProfile.MEDIUM -> 45
            PerformanceProfile.LOW -> 30
            PerformanceProfile.CRITICAL -> 24
        }
    }
    
    /**
     * Get recommended animation level (0-1) based on battery status
     */
    fun getRecommendedAnimationLevel(): Float {
        return when (getRecommendedPerformanceProfile()) {
            PerformanceProfile.HIGH -> 1.0f
            PerformanceProfile.MEDIUM -> 0.7f
            PerformanceProfile.LOW -> 0.4f
            PerformanceProfile.CRITICAL -> 0.2f
        }
    }
    
    /**
     * Get whether to show effects like particles and animations
     */
    fun shouldShowEffects(): Boolean {
        val profile = getRecommendedPerformanceProfile()
        return profile == PerformanceProfile.HIGH || profile == PerformanceProfile.MEDIUM
    }
    
    /**
     * Get whether to show ghost piece preview
     */
    fun shouldShowGhostPiece(): Boolean {
        return getRecommendedPerformanceProfile() != PerformanceProfile.CRITICAL
    }
    
    /**
     * Get whether to show grid lines
     */
    fun shouldShowGridLines(): Boolean {
        val profile = getRecommendedPerformanceProfile()
        return profile == PerformanceProfile.HIGH || profile == PerformanceProfile.MEDIUM
    }
    
    /**
     * Get whether to use hardware acceleration
     */
    fun shouldUseHardwareAcceleration(): Boolean {
        return !isPowerSaveMode
    }
    
    companion object {
        private const val TAG = "BatteryOptimizer"
    }
}
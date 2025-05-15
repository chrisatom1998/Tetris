package com.tetris.game

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

/**
 * Manages touch and accelerometer input for the Tetris game.
 * Handles gestures like swipes and taps, as well as optional accelerometer integration.
 */
class InputManager(
    context: Context,
    private val listener: InputListener
) : View.OnTouchListener, SensorEventListener {

    // Gesture detector for processing touch events
    private val gestureDetector: GestureDetector
    
    // Sensor manager for accelerometer
    private val sensorManager: SensorManager
    private val accelerometer: Sensor?
    
    // Settings
    private var useAccelerometer = false
    private var sensitivityX = 1.5f  // Lower = more sensitive
    private var doubleTapEnabled = true
    private var touchDelay = 100L // Minimum time between touch actions in ms
    
    // State
    private var lastActionTime = 0L
    private var lastX = 0f
    private var lastMoveDirection = 0  // -1 = left, 0 = none, 1 = right
    
    init {
        // Initialize gesture detector
        gestureDetector = GestureDetector(context, GestureListener())
        
        // Initialize sensor manager and accelerometer
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
    
    /**
     * Enable or disable accelerometer control
     */
    fun setAccelerometerEnabled(enabled: Boolean) {
        useAccelerometer = enabled
        if (enabled && accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        } else {
            sensorManager.unregisterListener(this)
        }
    }
    
    /**
     * Set sensitivity for accelerometer and gestures
     */
    fun setSensitivity(sensitivity: Float) {
        // Scale from 0.0 to 1.0 to appropriate range (higher sensitivity = lower threshold)
        sensitivityX = 3.0f - (sensitivity * 2.5f)
    }
    
    /**
     * Set whether double tap is enabled for hold piece
     */
    fun setDoubleTapEnabled(enabled: Boolean) {
        doubleTapEnabled = enabled
    }
    
    /**
     * Touch listener implementation
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event == null) return false
        
        // Let the gesture detector handle the event
        val result = gestureDetector.onTouchEvent(event)
        
        // Handle action up events that aren't caught by the gesture detector
        if (event.action == MotionEvent.ACTION_UP) {
            listener.onReleaseDown()
        }
        
        return result
    }
    
    /**
     * Sensor event listener implementation for accelerometer
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (!useAccelerometer || event == null) return
        
        // Only care about accelerometer events
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            // Check if enough time has passed since last action
            val now = SystemClock.uptimeMillis()
            if (now - lastActionTime < touchDelay) return
            
            // Get X tilt (landscape orientation)
            val x = event.values[0]
            
            // Determine if the device is tilted enough to move the piece
            if (x > sensitivityX && lastMoveDirection != -1) {
                // Tilted left
                listener.onMove(InputDirection.LEFT)
                lastMoveDirection = -1
                lastActionTime = now
            } else if (x < -sensitivityX && lastMoveDirection != 1) {
                // Tilted right
                listener.onMove(InputDirection.RIGHT)
                lastMoveDirection = 1
                lastActionTime = now
            } else if (abs(x) < sensitivityX * 0.5f) {
                // Back to neutral
                lastMoveDirection = 0
            }
        }
    }
    
    /**
     * Sensor accuracy changed (not used but required by interface)
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this implementation
    }
    
    /**
     * Clean up resources
     */
    fun dispose() {
        sensorManager.unregisterListener(this)
    }
    
    /**
     * Inner class to handle gestures
     */
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        
        /**
         * Handle single taps for rotation
         */
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            // Check if enough time has passed since last action
            val now = SystemClock.uptimeMillis()
            if (now - lastActionTime < touchDelay) return false
            
            listener.onRotate(true) // Clockwise rotation
            lastActionTime = now
            return true
        }
        
        /**
         * Handle double taps for hold piece
         */
        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (!doubleTapEnabled) return false
            
            // Check if enough time has passed since last action
            val now = SystemClock.uptimeMillis()
            if (now - lastActionTime < touchDelay) return false
            
            listener.onHold()
            lastActionTime = now
            return true
        }
        
        /**
         * Handle long press for pause
         */
        override fun onLongPress(e: MotionEvent) {
            listener.onPause()
        }
        
        /**
         * Handle scrolling for piece movement
         */
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (e1 == null) return false
            
            // Check if enough time has passed since last action
            val now = SystemClock.uptimeMillis()
            if (now - lastActionTime < touchDelay / 2) return false
            
            // Determine the primary direction of the scroll
            val absX = abs(distanceX)
            val absY = abs(distanceY)
            
            if (absY > absX) {
                // Vertical scroll
                if (distanceY > 0) {
                    // Scroll up (hard drop)
                    listener.onHardDrop()
                } else {
                    // Scroll down (soft drop)
                    listener.onSoftDrop(true)
                }
            } else {
                // Horizontal scroll
                if (distanceX > 0) {
                    // Scroll right (move left)
                    listener.onMove(InputDirection.LEFT)
                } else {
                    // Scroll left (move right)
                    listener.onMove(InputDirection.RIGHT)
                }
            }
            
            lastActionTime = now
            return true
        }
        
        /**
         * Handle flings for faster movement
         */
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 == null) return false
            
            // Check if enough time has passed since last action
            val now = SystemClock.uptimeMillis()
            if (now - lastActionTime < touchDelay) return false
            
            // Determine the primary direction of the fling
            val absX = abs(velocityX)
            val absY = abs(velocityY)
            
            // Only process strong flings
            val minVelocity = 1000
            if (absX < minVelocity && absY < minVelocity) return false
            
            if (absY > absX) {
                // Vertical fling
                if (velocityY < 0) {
                    // Fling up (hard drop)
                    listener.onHardDrop()
                } else {
                    // Fling down (soft drop)
                    listener.onSoftDrop(true)
                }
            } else {
                // Horizontal fling
                if (velocityX < 0) {
                    // Fling left (move right)
                    listener.onMove(InputDirection.RIGHT)
                } else {
                    // Fling right (move left)
                    listener.onMove(InputDirection.LEFT)
                }
            }
            
            lastActionTime = now
            return true
        }
        
        /**
         * Handle down event to track last touch position
         */
        override fun onDown(e: MotionEvent): Boolean {
            lastX = e.x
            return true
        }
    }
    
    /**
     * Enum defining input directions
     */
    enum class InputDirection {
        LEFT, RIGHT, DOWN
    }
    
    /**
     * Interface for input event callbacks
     */
    interface InputListener {
        fun onMove(direction: InputDirection)
        fun onRotate(clockwise: Boolean)
        fun onSoftDrop(pressed: Boolean)
        fun onReleaseDown()
        fun onHardDrop()
        fun onHold()
        fun onPause()
    }
}
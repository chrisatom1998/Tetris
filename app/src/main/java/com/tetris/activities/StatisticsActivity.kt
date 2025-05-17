package com.tetris.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tetris.R // Assuming your R file is com.tetris.R

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // You might want to set a content view here, e.g.:
        // setContentView(R.layout.activity_statistics)
        // For now, we'll leave it minimal to resolve the class not found error.

        // Support action bar and back navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
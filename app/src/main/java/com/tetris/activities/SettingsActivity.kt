package com.tetris.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // You might want to set a content view here, e.g.:
        // setContentView(R.layout.activity_settings)
        // For now, we'll leave it minimal to resolve the class not found error.

        // Support action bar and back navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
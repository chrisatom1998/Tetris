package com.tetris.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tetris.data.converters.BoardConverter
import com.tetris.data.converters.DateConverter
import com.tetris.data.converters.PieceConverter
import com.tetris.data.dao.*
import com.tetris.data.generated.RoomDatabaseGeneratedImpl
import com.tetris.model.*

/**
 * Main application database using Room
 * This implementation manually creates the DAOs to bypass KAPT processing
 */
class AppDatabaseFix {
    companion object {
        private const val DATABASE_NAME = "tetris_database"
        
        // Create a manual implementation of the database
        @Volatile private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.example.sorucozumpaylasimuygulamasi.roomSoru

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Questions::class], version = 1)
abstract class AppDatabaseSoru : RoomDatabase() {
    abstract fun questionDao(): questionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabaseSoru? = null

        fun getDatabase(context: Context): AppDatabaseSoru {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabaseSoru::class.java,
                    "questions_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

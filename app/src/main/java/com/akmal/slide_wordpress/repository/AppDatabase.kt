// File: data/AppDatabase.kt
package com.akmal.slide_wordpress.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akmal.slide_wordpress.dao.AppDao
import com.akmal.slide_wordpress.entities.ArticleEntity
import com.akmal.slide_wordpress.entities.CategoryEntity
import com.akmal.slide_wordpress.entities.MediaEntity
import com.akmal.slide_wordpress.utils.Converters

@Database(entities = [ArticleEntity::class, CategoryEntity::class, MediaEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wordpress_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.aravind.pepultask.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aravind.pepultask.data.local.db.dao.Converters
import com.aravind.pepultask.data.local.db.dao.PostDao
import com.aravind.pepultask.data.local.db.entity.PostEntity
import javax.inject.Singleton

@TypeConverters(Converters::class)
@Singleton
@Database(
    entities = [
        PostEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class DatabaseService : RoomDatabase() {

    abstract fun postDao(): PostDao
}
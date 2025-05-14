package com.ilham.umkm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilham.umkm.data.ProductDao
import com.ilham.umkm.data.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(contex: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    contex.applicationContext,
                    AppDatabase::class.java,
                    "umkm_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
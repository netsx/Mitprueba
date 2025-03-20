package com.example.mit.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mit.dao.TarjetaDao
import com.example.mit.dao.TransaccionDao
import com.example.mit.tables.TarjetaEntity
import com.example.mit.tables.TransaccionEntity

@Database(entities = [TarjetaEntity::class, TransaccionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tarjetaDao(): TarjetaDao
    abstract fun transaccionDao(): TransaccionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tarjeta_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

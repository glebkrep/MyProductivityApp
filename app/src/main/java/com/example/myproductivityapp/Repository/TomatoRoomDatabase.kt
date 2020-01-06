package com.example.myproductivityapp.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Tomato::class),version = 1)
abstract class TomatoRoomDatabase: RoomDatabase() {

    abstract fun tomatoDao(): TomatoDao

    companion object{
        @Volatile
        private var INSTANCE: TomatoRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TomatoRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,
                        TomatoRoomDatabase::class.java,"tomato_db").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }

            }
        }
    }
}
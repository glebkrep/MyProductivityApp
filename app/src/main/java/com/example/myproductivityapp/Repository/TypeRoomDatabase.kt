package com.example.myproductivityapp.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Type::class),version = 1)
abstract class TypeRoomDatabase: RoomDatabase() {

    abstract fun typeDao(): TypeDao

    companion object{
        @Volatile
        private var INSTANCE: TypeRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TypeRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,
                        TypeRoomDatabase::class.java,"type_db").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }

            }
        }
    }
}
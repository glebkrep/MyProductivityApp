package com.example.myproductivityapp.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TypeDao{

    @Query("select * from type_table")
    fun getAllTypes(): LiveData<List<Type>>

    @Query("select * from type_table where id=:id")
    fun getTypeById(id:Int):LiveData<List<Type>>

    @Insert
    suspend fun insert(type: Type)

    @Query("update type_table set name=:name, daily_time=:desiredDailyTime where id=:id")
    suspend fun updateType(id:Int,name:String,desiredDailyTime:Long)


    @Query("delete from type_table")
    suspend fun deleteAll()

}
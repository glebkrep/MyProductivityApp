package com.example.myproductivityapp.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface TomatoDao{

    @Query("select * from tomato_table where is_current=:mTrue")
    fun getCurrentTomato(mTrue:Boolean = true): LiveData<List<Tomato>>

    @Query("select * from tomato_table where start_time >=:startOfDay ")
    fun getTomatoForDay(startOfDay:Long):LiveData<List<Tomato>>

    @Insert
    suspend fun insert(tomato: Tomato)

    @Query("update tomato_table set end_time=:endTime where id=:id")
    suspend fun addEndTime(id:Int,endTime:Long)

    @Query("update tomato_table set is_current=:mFalse where id=:id")
    suspend fun markInactive(id:Int,mFalse:Boolean=false)

    @Query("delete from tomato_table")
    suspend fun deleteAll()
}
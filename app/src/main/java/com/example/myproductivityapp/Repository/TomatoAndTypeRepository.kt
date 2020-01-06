package com.example.myproductivityapp.Repository

import androidx.lifecycle.LiveData

class TomatoAndTypeRepository(private val tomatoDao: TomatoDao,private val typeDao:TypeDao) {

    val currentTomato:LiveData<List<Tomato>> = tomatoDao.getCurrentTomato()
    val allTypes:LiveData<List<Type>> = typeDao.getAllTypes()

    //TYPE

    suspend fun insertType(type:Type){
        typeDao.insert(type)
    }

    suspend fun updateType(id:Int,name:String,desiredDailyTime:Long){
        typeDao.updateType(id,name,desiredDailyTime)
    }

    //TOMATO

    fun getTomatoForDay(startOfDay:Long):LiveData<List<Tomato>>{
        return tomatoDao.getTomatoForDay(startOfDay)
    }

    suspend fun insertTomato(tomato:Tomato){
        tomatoDao.insert(tomato)
    }

    suspend fun addEndTimeTomato(id:Int,endTime:Long){
        tomatoDao.addEndTime(id,endTime)
    }

    suspend fun markInactiveTomato(id:Int){
        tomatoDao.markInactive(id)
    }
}

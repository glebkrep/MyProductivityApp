package com.example.myproductivityapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myproductivityapp.Repository.*
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel(application: Application):AndroidViewModel(application) {
    private val repository: TomatoAndTypeRepository

    val lastTomato: LiveData<Tomato>
    val allTypes:LiveData<List<Type>>
    val tomatoForDay:LiveData<List<Tomato>>
    val allTomatos:LiveData<List<Tomato>>

    init {
        val tomatoDao =
            TomatoRoomDatabase.getDatabase(application, viewModelScope).tomatoDao()
        val typeDao =
            TypeRoomDatabase.getDatabase(application,viewModelScope).typeDao()
        repository = TomatoAndTypeRepository(typeDao = typeDao,tomatoDao = tomatoDao)


        lastTomato = repository.lastTomato
        allTypes = repository.allTypes
        allTomatos = repository.allTomatos

        //TODO: to sep. function
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY,0)
        cal.set(Calendar.MINUTE,0)
        cal.set(Calendar.SECOND,0)
        tomatoForDay = repository.getTomatoForDay(cal.timeInMillis)
    }



    //TYPE
    fun typeById(id:Int) = repository.getTypeById(id = id)

    fun typeInsert(type:Type) = viewModelScope.launch {
        repository.insertType(type)
    }

    fun typeUpdate(id:Int,name:String,desiredDailyTime:Long) = viewModelScope.launch {
        repository.updateType(id, name, desiredDailyTime)
    }



    //TOMATO
    fun tomatoInsert(tomato: Tomato) = viewModelScope.launch {
        repository.insertTomato(tomato)
    }
    fun tomatoAddEndTime(id:Int,endTime:Long) = viewModelScope.launch {
        repository.addEndTimeTomato(id,endTime)
    }

    fun tomatoMarkInactive(id:Int) = viewModelScope.launch {
        repository.markInactiveTomato(id)
    }



    fun stopTomato(tomato:Tomato) = viewModelScope.launch {
        tomatoAddEndTime(tomato.id!!,System.currentTimeMillis())
        tomatoMarkInactive(tomato.id!!)
    }

}
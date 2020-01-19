package com.example.myproductivityapp

import android.content.Context
import android.text.format.DateFormat
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider


object Util {
//    val TOMATO_TIME = 60L*35L*1000L
    val TOMATO_TIME = 1000L*5L
//    val CHILL_TIME = 1000L * 60L * 5L
    val CHILL_TIME = 1000L*5L
    fun showShortToast(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    fun getViewModel(activity:FragmentActivity):MainActivityViewModel=
        activity.run {
            val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
            ViewModelProvider(this,factory).get(MainActivityViewModel::class.java)
        }

    fun millisToMS(millis:Long):String{
        val seconds = millis/1000L
        val s: Long = seconds % 60
        val m: Long = seconds / 60 % 60
        return String.format("%02d:%02d", m, s)
    }


    fun fromTo(startTime: Long, endTime: Long?): String {
        return "from " + millisToDate(startTime) + " to " + millisToDate(endTime!!)
    }

    private fun millisToDate(millis:Long):String{
        val dateFormat = "dd/MM/yyyy HH:mm"
        return DateFormat.format(dateFormat, millis).toString()
    }

    fun totalTime(endTime: Long?, startTime: Long): String {
        val time = endTime!!-startTime
        var dateFormat:String = ""
        if (time<1000L*60L*60L){
             dateFormat = "mm:ss"
        }
        else dateFormat = "HH:mm:ss"
        return DateFormat.format(dateFormat,time).toString()
    }

}

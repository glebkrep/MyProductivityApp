package com.example.myproductivityapp

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

object Util {
    val TOMATO_TIME = 60L*35L*1000L
    val CHILL_TIME = 1000L * 60L * 5L
    fun showShortToast(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    fun getViewModel(activity:FragmentActivity):MainActivityViewModel=
        activity.run {
            val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
            ViewModelProvider(this,factory).get(MainActivityViewModel::class.java)
        }
}

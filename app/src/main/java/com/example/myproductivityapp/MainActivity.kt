package com.example.myproductivityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    init{
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object{
        private var instance: MainActivity? = null
        fun getViewModel(activity:FragmentActivity):MainActivityViewModel{
            val factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
            return ViewModelProvider(instance!!, factory).get(MainActivityViewModel::class.java)

        }
    }
}

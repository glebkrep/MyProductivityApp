package com.example.myproductivityapp.Notifications

import android.content.Context
import androidx.work.Data
import com.example.myproductivityapp.Utils.Util

object NotifUtils {

    private fun createWorkInputData(title: String, text: String, id: Int): Data {
        return Data.Builder()
            .putString(NotifyWorker.EXTRA_TITLE, title)
            .putString(NotifyWorker.EXTRA_TEXT, text)
            .putInt(NotifyWorker.EXTRA_ID, id)
            .build()
    }

    //TODO: notification
    fun scheduleNotification(mContext:Context?,isTomato:Boolean){

        mContext?.let {
            NotifyWorker.cancelReminder(tag,it)
            if (isTomato){
                val notificationData =
                    createWorkInputData("Tomato has ended, you can rest","Your tomato has ended, click 'chill' to take some rest",0)
                NotifyWorker.scheduleReminder(Util.TOMATO_TIME,notificationData,tag,it)
            }
            else{
                val notificationData =
                    createWorkInputData("Chill has ended, get to work!","Back to Work!",0)
                NotifyWorker.scheduleReminder(Util.CHILL_TIME,notificationData,tag,it)
            }
        }
    }

    fun stopNotif(mContext: Context?){
        mContext?.let {
            NotifyWorker.cancelReminder(tag,it)
        }
    }

    const val tag = "TOMATOTAGFORREAL"
}
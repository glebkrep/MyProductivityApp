package com.example.myproductivityapp.Fragments


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.Notifications.NotifUtils
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Repository.Type
import com.example.myproductivityapp.Utils.Util
import kotlinx.android.synthetic.main.fragment_chill.*

class ChillFragment : Fragment(R.layout.fragment_chill) {

    lateinit var viewModel: MainActivityViewModel
    lateinit var currentTomato: Tomato
    lateinit var currentType: Type
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)

        viewModel.lastTomato.observe(this, Observer {
            it?.let {
                currentTomato = it
                if (!currentTomato.isCurrent){
                    findNavController().navigate(R.id.action_chillFragment_to_changeTypeFragment)
                }
                else if (currentTomato.endTime==null){
                    findNavController().navigate(R.id.action_chillFragment_to_tomatoFragment)
                }
                viewModel.typeById(currentTomato.type).observe(this, Observer {
                    if (!it.isNullOrEmpty()){
                        currentType = it.first()

                        updateUi()
                    }
                })
            }
        })

        btn_stop_chill.setOnClickListener {
            NotifUtils.stopNotif(context)
            viewModel.tomatoMarkInactive(currentTomato.id!!)
            findNavController().navigate(R.id.action_chillFragment_to_changeTypeFragment)

        }
        btn_back_to_work.setOnClickListener {
            NotifUtils.scheduleNotification(context,true)
            viewModel.tomatoMarkInactive(currentTomato.id!!)
            val newTomato = Tomato(type = currentType.id!!)
            viewModel.tomatoInsert(newTomato)
            findNavController().navigate(R.id.action_chillFragment_to_tomatoFragment)

        }
        btn_change_type.setOnClickListener {
            NotifUtils.stopNotif(context)
            viewModel.tomatoMarkInactive(currentTomato.id!!)
            findNavController().navigate(R.id.action_chillFragment_to_changeTypeFragment)

        }

    }


    private fun updateUi(){
        text_chill_tomato_type.text = "Type: " + currentType.name
        val millisLeft= ((Util.CHILL_TIME - (System.currentTimeMillis() - currentTomato.endTime!!)))

        val timer = MyTimer(millisLeft,text_chill_time,currentTomato.endTime!!)
        timer.start()
    }

    //todo unify
    class MyTimer(millisLeft:Long, val timerTextView: TextView, val endTime:Long): CountDownTimer(millisLeft,1000){
        override fun onFinish() {
            afterTimer(timerTextView = timerTextView,startTime = endTime).start()
        }

        override fun onTick(millisUntilFinished: Long) {
            timerTextView.text = Util.millisToMS(millisUntilFinished)
        }

    }
    class afterTimer(val millisLeft: Long = 100000000L,val timerTextView:TextView,val startTime:Long):CountDownTimer(millisLeft,1000){
        override fun onFinish() {
            timerTextView.text = "That's it!"
        }

        override fun onTick(millisUntilFinished: Long) {

            timerTextView.text = "-"+ Util.millisToMS(System.currentTimeMillis()-(Util.TOMATO_TIME+startTime))
        }

    }
}

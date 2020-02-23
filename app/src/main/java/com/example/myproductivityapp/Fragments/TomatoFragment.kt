package com.example.myproductivityapp.Fragments


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.Notifications.NotifUtils
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Repository.Type
import com.example.myproductivityapp.Utils.Util
import kotlinx.android.synthetic.main.fragment_tomato.*

class TomatoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: util function for getting viewModel

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tomato, container, false)
    }
    lateinit var viewModel: MainActivityViewModel
    lateinit var currentTomato:Tomato
    lateinit var currentType:Type
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)

        viewModel.lastTomato.observe(this, Observer {
            it?.let {
                currentTomato = it
                if (!currentTomato.isCurrent){
                    findNavController().navigate(R.id.action_tomatoFragment_to_changeTypeFragment)
                }
                else if (currentTomato.endTime!=null){
                    findNavController().navigate(R.id.action_tomatoFragment_to_chillFragment)
                }
                viewModel.typeById(currentTomato.type).observe(this, Observer {
                    if(!it.isNullOrEmpty()){
                        currentType = it.first()
                        updateUi()
                    }
                })

            }
        })

        StopButton.setOnClickListener {
            NotifUtils.stopNotif(context)
            viewModel.stopTomato(currentTomato)
            findNavController().navigate(R.id.action_tomatoFragment_to_changeTypeFragment)
        }

        ChillButton.setOnClickListener {
            NotifUtils.scheduleNotification(context,false)
            val endTime = System.currentTimeMillis()

            viewModel.tomatoAddEndTime(currentTomato.id!!,endTime)
            findNavController().navigate(R.id.action_tomatoFragment_to_chillFragment)
        }


    }

    fun updateUi(){
        TomatoTypeText.text = "Type: " + currentType.name

        //TODO: to util class
        //TODO move tomato_time to constants
        val millisLeft= ((Util.TOMATO_TIME - (System.currentTimeMillis() - currentTomato.startTime)))

        val timer = myTimer(millisLeft,TimeText,currentTomato.startTime)
        timer.start()
    }

    //TODO: get rid of classes here
    class myTimer(val millisLeft:Long,val timerTextView:TextView,val startTime:Long):CountDownTimer(millisLeft,1000){
        override fun onFinish() {
            afterTimer(timerTextView = timerTextView,startTime =startTime ).start()
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

package com.example.myproductivityapp.Fragments


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Repository.Type
import com.example.myproductivityapp.Util
import kotlinx.android.synthetic.main.fragment_tomato.*
import kotlin.reflect.typeOf
import kotlin.system.measureTimeMillis

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
        currentTomato = arguments!!.getParcelable<Tomato>("currentTomato")!!
        currentType = arguments!!.getParcelable<Type>("currentType")!!

        updateUiWithTomato(currentTomato)


        StopButton.setOnClickListener {
            //TODO:
            //3) navigateTo(changeType)
            //4) turn off scheduled notification for end of tomato
            viewModel.stopTomato(currentTomato)
            findNavController().navigate(R.id.action_tomatoFragment_to_changeTypeFragment)
        }

        ChillButton.setOnClickListener {
            //TODO:
            //4) turn off scheduled notif for end of tomato
            //5) schedule notif for end of chill
            val endTime = System.currentTimeMillis()

            viewModel.tomatoAddEndTime(currentTomato.id!!,endTime)
            val bundle = Bundle()
            currentTomato.endTime = endTime
            bundle.putParcelable("currentTomato",currentTomato)
            bundle.putParcelable("currentType",currentType)
            findNavController().navigate(R.id.action_tomatoFragment_to_chillFragment2,bundle)
        }


    }

    fun updateUiWithTomato(tomato:Tomato){
        TomatoTypeText.text = "Type: " + currentType.name

        //TODO: to util class
        val millisLeft= ((Util.TOMATO_TIME - (System.currentTimeMillis() - tomato.startTime)))

        val timer = myTimer(millisLeft,TimeText)
    }

    class myTimer(millisLeft:Long,val timerTextView:TextView):CountDownTimer(millisLeft,1000){
        override fun onFinish() {
            TODO("not implemented")
            // TODO this should start new timer that goes beyond 0:0
            timerTextView.text = "That's it!"
        }

        override fun onTick(millisUntilFinished: Long) {
            timerTextView.text = (millisUntilFinished/1000).toString()
        }

    }
}

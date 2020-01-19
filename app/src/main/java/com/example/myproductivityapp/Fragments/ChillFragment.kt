package com.example.myproductivityapp.Fragments


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Repository.Type
import com.example.myproductivityapp.Util
import kotlinx.android.synthetic.main.fragment_chill.*
import kotlinx.android.synthetic.main.fragment_tomato.*

/**
 * A simple [Fragment] subclass.
 */
class ChillFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chill, container, false)
    }


    lateinit var viewModel: MainActivityViewModel
    lateinit var currentTomato: Tomato
    lateinit var currentType: Type
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)
        currentTomato = arguments!!.getParcelable<Tomato>("currentTomato")!!
        currentType = arguments!!.getParcelable<Type>("currentType")!!

        updateUi()


        chillStopButton.setOnClickListener {

            //TODO
            //2) turn off scheduled notification for end of chill
            viewModel.tomatoMarkInactive(currentTomato.id!!)
            findNavController().navigate(R.id.action_chillFragment_to_changeTypeFragment)

        }
        BackToWorkButton.setOnClickListener {
            //TODO:
            //1) turn off sched. notif for end of chill
            //4) set notif for end of new tomato
            viewModel.tomatoMarkInactive(currentTomato.id!!)
            val newTomato = Tomato(type = currentType.id!!)
            viewModel.tomatoInsert(newTomato)
            findNavController().navigate(R.id.action_chillFragment_to_splashFragment)

        }
        ChangeTypeButton.setOnClickListener {
            //TODO
            //2) turn off scheduled notification for end of chill
            viewModel.tomatoMarkInactive(currentTomato.id!!)
            findNavController().navigate(R.id.action_chillFragment_to_changeTypeFragment)

        }

    }


    fun updateUi(){
        ChillTypeText.text = "Type: " + currentType.name

        //TODO: to util class
        val millisLeft= ((Util.CHILL_TIME - (System.currentTimeMillis() - currentTomato.endTime!!)))

        val timer = myTimer(millisLeft,ChillTimeText)
        timer.start()
    }

    class myTimer(millisLeft:Long,val timerTextView: TextView): CountDownTimer(millisLeft,1000){
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

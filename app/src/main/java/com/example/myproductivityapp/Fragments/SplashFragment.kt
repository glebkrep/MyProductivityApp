package com.example.myproductivityapp.Fragments


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Util

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    lateinit var viewModel: MainActivityViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)


        viewModel.currentTomato.observe(this, Observer {
            if (it.isEmpty()){
                findNavController().navigate(R.id.action_splashFragment_to_changeTypeFragment)
            }
            else{
                val tomato = it.first()
                viewModel.typeById(tomato.type).observe(this, Observer {
                    val type = it.first()
                    val bundle = Bundle()
                    bundle.putParcelable("currentTomato",tomato)
                    bundle.putParcelable("currentType",type)
                    if (tomato.endTime == null){
                        findNavController().navigate(R.id.action_splashFragment_to_tomatoFragment,bundle)
                    }
                    else{
                        findNavController().navigate(R.id.action_splashFragment_to_chillFragment,bundle)
                    }
                })

            }
        })


    }
}

package com.example.myproductivityapp.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Utils.Util

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var viewModel: MainActivityViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)


        viewModel.lastTomato.observe(this, Observer {
            if (it==null){
                findNavController().navigate(R.id.action_splashFragment_to_changeTypeFragment)
            }
            else{
                if (it.isCurrent){
                    if (it.endTime==null) findNavController().navigate(R.id.action_splashFragment_to_tomatoFragment)
                    else findNavController().navigate(R.id.action_splashFragment_to_chillFragment)
                }
                else findNavController().navigate(R.id.action_splashFragment_to_changeTypeFragment)
            }

        })

    }
}

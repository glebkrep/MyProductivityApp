package com.example.myproductivityapp.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Util
import kotlinx.android.synthetic.main.fragment_change_type.*
import java.lang.reflect.Type

/**
 * A simple [Fragment] subclass.
 */
class ChangeTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_type, container, false)
    }

    lateinit var viewModel: MainActivityViewModel
    lateinit var allTypes:List<com.example.myproductivityapp.Repository.Type>
    var chosenId = -1
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)

        viewModel.allTypes.observe(this, Observer {
            if (it.isNotEmpty()){
                allTypes = it
                val namesList = mutableListOf<String>()
                for (type in it){
                    namesList.add(type.name)
                }
                namesList.add("Add new...")
                ChangeTypeSpinner.setItems(namesList.toList())
                ChangeTypeSpinner.setOnItemSelectedListener { view, position, id, item->
                    Util.showShortToast(context!!,item.toString())
                    if (item.toString() =="Add new..."){
                        findNavController().navigate(R.id.action_changeTypeFragment_to_newTypeFragment)
                    }
                }

            }
            else{
                allTypes = emptyList()
                ChangeTypeSpinner.setItems(listOf("Add new..."))
                ChangeTypeSpinner.setOnItemSelectedListener { view, position, id, item ->
                    Util.showShortToast(context!!,item.toString())
                    if (item.toString() =="Add new..."){
                        findNavController().navigate(R.id.action_changeTypeFragment_to_newTypeFragment)
                    }
                }
            }
        })

        StartTomatoButton.setOnClickListener {
            val type = allTypes[ChangeTypeSpinner.selectedIndex]
            //TODO:
            //2) start notification for end of tomato
            val newTomato = Tomato(type = type.id!!,isCurrent = true)
            viewModel.tomatoInsert(newTomato)
//            val bundle = Bundle()
//            bundle.putParcelable("currentTomato",newTomato)
//            bundle.putParcelable("currentType",type)
            findNavController().navigate(R.id.action_changeTypeFragment_to_splashFragment)
        }

    }
}

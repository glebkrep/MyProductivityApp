package com.example.myproductivityapp.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.PastTomatosAdapter
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Util
import kotlinx.android.synthetic.main.fragment_change_type.*
import java.lang.reflect.Type


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
    lateinit var allTomatos:List<Tomato>
    lateinit var adapter:PastTomatosAdapter
    var chosenId = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)

        viewModel.allTypes.observe(this, Observer {
            //TODO: move to sep function
            if (it.isNotEmpty()){
                allTypes = it

                //TODO sep function
                viewModel.tomatoForDay.observe(this, Observer {
                    if (it.isNotEmpty()){
                        adapter = PastTomatosAdapter(context!!)
                        ChangeTypePastTomatosRV.adapter = adapter
                        ChangeTypePastTomatosRV.layoutManager = LinearLayoutManager(context!!)
                        //todo: set tomatos with their types map
                        val typesMap = getTypesMap()
                        adapter.setTomatosAndTypes(it,typesMap)
                    }
                })

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


        viewModel.lastTomato.observe(this, Observer {
            it?.let {
                if (it.isCurrent){
                    if (it.endTime==null) findNavController().navigate(R.id.action_changeTypeFragment_to_chillFragment)
                    else findNavController().navigate(R.id.action_changeTypeFragment_to_tomatoFragment)
                }
            }
        })





        StartTomatoButton.setOnClickListener {
            if (allTypes.isEmpty()){
                findNavController().navigate(R.id.action_changeTypeFragment_to_newTypeFragment)
                return@setOnClickListener
            }
            val type = allTypes[ChangeTypeSpinner.selectedIndex]
            //TODO:
            //2) start notification for end of tomato
            val newTomato = Tomato(type = type.id!!,isCurrent = true)
            viewModel.tomatoInsert(newTomato)
            findNavController().navigate(R.id.action_changeTypeFragment_to_tomatoFragment)
        }

    }

    private fun getTypesMap(): Map<Int, String> {
        var typesMaps:MutableMap<Int,String> = mutableMapOf()

        //todo should check if alltypes is present
        for (type in allTypes){
            typesMaps.put(type.id!!,type.name)
        }
        return typesMaps.toMap()

    }
}

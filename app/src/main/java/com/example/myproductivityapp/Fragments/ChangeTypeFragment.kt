package com.example.myproductivityapp.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.Notifications.NotifUtils
import com.example.myproductivityapp.PastTomatosAdapter
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Tomato
import com.example.myproductivityapp.Utils.Util
import kotlinx.android.synthetic.main.fragment_change_type.*

//TODO remove already sent notification form app

class ChangeTypeFragment : Fragment(R.layout.fragment_change_type) {

    lateinit var viewModel: MainActivityViewModel
    lateinit var allTypes:List<com.example.myproductivityapp.Repository.Type>
    lateinit var adapter:PastTomatosAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Util.getViewModel(activity!!)

        viewModel.allTypes.observe(this, Observer {
            //TODO: move to sep function
            if (it.isNotEmpty()){
                allTypes = it

                //TODO sep function (might need a change to day tomatos)
                viewModel.allTomatos.observe(this, Observer {
                    if (it.isNotEmpty()){
                        adapter = PastTomatosAdapter(context!!)
                        recycler_past_tomatos.adapter = adapter
                        recycler_past_tomatos.layoutManager = LinearLayoutManager(context!!)
                        val typesMap = getTypesMap()
                        adapter.setTomatosAndTypes(it,typesMap)
                    }
                })

                val namesList = mutableListOf<String>()
                for (type in it){
                    namesList.add(type.name)
                }
                namesList.add("Add new...")
                spinner_change_type.setItems(namesList.toList())
                spinner_change_type.setOnItemSelectedListener { view, position, id, item->
                    Util.showShortToast(context!!,item.toString())
                    if (item.toString() =="Add new..."){
                        findNavController().navigate(R.id.action_changeTypeFragment_to_newTypeFragment)
                    }
                }

            }
            else{
                allTypes = emptyList()
                spinner_change_type.setItems(listOf("Add new..."))
                spinner_change_type.setOnItemSelectedListener { view, position, id, item ->
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





        btn_start_tomato.setOnClickListener {
            if (allTypes.isEmpty()){
                findNavController().navigate(R.id.action_changeTypeFragment_to_newTypeFragment)
                return@setOnClickListener
            }
            val type = allTypes[spinner_change_type.selectedIndex]
            val newTomato = Tomato(type = type.id!!,isCurrent = true)

            NotifUtils.scheduleNotification(context,true)

            viewModel.tomatoInsert(newTomato)
            findNavController().navigate(R.id.action_changeTypeFragment_to_tomatoFragment)
        }

    }

    private fun getTypesMap(): Map<Int, String> {
        var typesMaps:MutableMap<Int,String> = mutableMapOf()

        if (allTypes.isNullOrEmpty()) return emptyMap()

        for (type in allTypes){
            typesMaps.put(type.id!!,type.name)
        }
        return typesMaps.toMap()

    }
}

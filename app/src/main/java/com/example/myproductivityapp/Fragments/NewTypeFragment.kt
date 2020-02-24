package com.example.myproductivityapp.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.myproductivityapp.MainActivityViewModel
import com.example.myproductivityapp.R
import com.example.myproductivityapp.Repository.Type
import com.example.myproductivityapp.TypesAdapter
import com.example.myproductivityapp.Utils.Util
import kotlinx.android.synthetic.main.fragment_new_type.*

class NewTypeFragment : Fragment(R.layout.fragment_new_type) {

    private lateinit var viewModel: MainActivityViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = Util.getViewModel(activity!!)

        val adapter = TypesAdapter(context!!)
        recycler_types.adapter = adapter
        recycler_types.layoutManager = LinearLayoutManager(context!!)

        viewModel.allTypes.observe(this, Observer {
            if (it.isNotEmpty()){
                adapter.setTypeList(it)
            }
        })

        fab_add_type.setOnClickListener {
            MaterialDialog(context!!).show {
                title(R.string.type_creation_dialog_name)
                cornerRadius(20f)
                input(hint = "Enter type name...",callback = {_,text->
                    viewModel.typeInsert(Type(name = text.toString(),desiredDailyTime = 0L))
                })
            }
        }
    }


}


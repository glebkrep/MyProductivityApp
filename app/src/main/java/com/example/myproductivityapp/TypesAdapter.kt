package com.example.myproductivityapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TypesAdapter(context: Context) : RecyclerView.Adapter<TypesAdapter.TypesViewHolder>() {
    private var typesList = emptyList<com.example.myproductivityapp.Repository.Type>()
    private var inflater = LayoutInflater.from(context)

    inner class TypesViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val typeName = itemView.findViewById<TextView>(R.id.text_type_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypesViewHolder {
        val itemView = inflater.inflate(R.layout.type_rv_item,parent,false)
        return TypesViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return typesList.size
    }

    override fun onBindViewHolder(holder: TypesViewHolder, position: Int) {

        val current = typesList[position]

        holder.typeName.text = current.name
    }

    fun setTypeList(types:List<com.example.myproductivityapp.Repository.Type>){
        this.typesList = types
        notifyDataSetChanged()
    }

}

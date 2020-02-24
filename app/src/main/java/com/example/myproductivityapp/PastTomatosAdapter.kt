package com.example.myproductivityapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myproductivityapp.Utils.Util

class PastTomatosAdapter(context: Context) : RecyclerView.Adapter<PastTomatosAdapter.PastTomatosViewHolder>() {
    private var tomatosList = emptyList<com.example.myproductivityapp.Repository.Tomato>()
    private var inflater = LayoutInflater.from(context)
    private var typesMap = mapOf<Int,String>()

    inner class PastTomatosViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val typeName = itemView.findViewById<TextView>(R.id.text_past_tomato_type)
        val totalTime = itemView.findViewById<TextView>(R.id.text_past_tomato_total_time)
        val fromTo = itemView.findViewById<TextView>(R.id.text_past_tomato_from_to)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastTomatosViewHolder {
        val itemView = inflater.inflate(R.layout.past_tomato_rv_item,parent,false)
        return PastTomatosViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return tomatosList.size
    }

    override fun onBindViewHolder(holder: PastTomatosViewHolder, position: Int) {

        val current = tomatosList[position]

        holder.typeName.text = typesMap.get(current.type)
        //todo: util function to display as H:M:S
        holder.fromTo.text = Util.fromTo(current.startTime,current.endTime)
        holder.totalTime.text = Util.totalTime(current.endTime,current.startTime)
    }

    fun setTomatosAndTypes(tomatos:List<com.example.myproductivityapp.Repository.Tomato>,typeMap:Map<Int,String>){
        this.tomatosList = tomatos
        this.typesMap = typeMap
        notifyDataSetChanged()
    }

}

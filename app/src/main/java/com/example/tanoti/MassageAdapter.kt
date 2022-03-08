package com.example.tanoti

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MassageAdapter(val context: Context,val massageList: ArrayList<Massage>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECIVE = 1
    val ITEM_SEND = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view :View = LayoutInflater.from(context).inflate(R.layout.recive,parent,false)
            return ReciveViewHolder(view)
        }else{

            val view :View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMassage = massageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMassage.text = currentMassage.massage

        }else{
            val viewHolder = holder as ReciveViewHolder
            holder.reciveMassage.text = currentMassage.massage
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMassage = massageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMassage.senderId)){
            return ITEM_SEND
        }
        else {
            return ITEM_RECIVE
        }
    }

    override fun getItemCount(): Int {
        return massageList.size
    }
    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMassage = itemView.findViewById<TextView>(R.id.txt_sent_massage)
    }
    class ReciveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val reciveMassage = itemView.findViewById<TextView>(R.id.txt_recive_massage)
    }


}
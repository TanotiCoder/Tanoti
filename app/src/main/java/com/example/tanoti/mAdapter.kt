package com.example.tanoti

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_layout.view.*

class mAdapter(val context: Context,val userList: ArrayList<User>):RecyclerView.Adapter<mAdapter.MHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        val inflater = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return MHolder(inflater)
    }
    override fun onBindViewHolder(holder: MHolder, position: Int) {
        val currentUser = userList[position]
            holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context,activity_chat::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }
    override fun getItemCount():Int {
      return userList.size
    }
    class MHolder(view: View):RecyclerView.ViewHolder(view) {
        var textName = view.findViewById<TextView>(R.id.user_list_id)
    }
}
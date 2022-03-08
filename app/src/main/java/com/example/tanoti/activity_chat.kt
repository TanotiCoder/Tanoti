package com.example.tanoti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class activity_chat : AppCompatActivity() {
    private lateinit var massageRecyclerView: RecyclerView
    private lateinit var massageBox: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var massageAdapter: MassageAdapter
    private lateinit var massageList: ArrayList<Massage>
    private lateinit var mDbref: DatabaseReference
    var reciverRoom: String? = null
    var senderRoom: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mDbref = FirebaseDatabase.getInstance().getReference()
        val name = intent.getStringExtra("name")
        val reciverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = reciverUid + senderUid
        reciverRoom = senderUid + reciverUid

        supportActionBar?.title = name
        massageRecyclerView = chatRecyclerview
        massageBox = findViewById(R.id.massageBox)
        sendButton = findViewById(R.id.sent_btn)
        massageList = ArrayList()
        massageAdapter = MassageAdapter(this, massageList)

        chatRecyclerview.layoutManager = LinearLayoutManager(this)
        chatRecyclerview.adapter = massageAdapter


        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    massageList.clear()

                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Massage::class.java)
                        massageList.add(message!!)
                    }
                    massageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        sendButton.setOnClickListener {
            val massage = massageBox.text.toString()
            val massageObject = Massage(massage, senderUid)
            if (massage.isNotEmpty()) {
                mDbref.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(massageObject).addOnSuccessListener {
                        mDbref.child("chats").child(reciverRoom!!).child("messages").push()
                            .setValue(massageObject)
                    }
            }
            massageBox.setText("")
        }
    }
}
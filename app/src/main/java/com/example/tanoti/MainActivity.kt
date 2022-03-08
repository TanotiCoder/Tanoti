package com.example.tanoti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: mAdapter
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        //val sai = listOf<String>("sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3","sai","sai2","sai3",)
        val  adapter = mAdapter(this, userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.adapter = adapter
        mDbRef.child("user").addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (firebaseAuth.currentUser?.uid!= currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            firebaseAuth.signOut()
            val intent = Intent(this,activity_login::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return true
    }
}